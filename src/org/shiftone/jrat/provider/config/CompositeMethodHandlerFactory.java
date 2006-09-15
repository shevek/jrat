package org.shiftone.jrat.provider.config;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class CompositeMethodHandlerFactory implements MethodHandlerFactory {

    private static final Logger LOG                    = Logger.getLogger(CompositeMethodHandlerFactory.class);
    private List                methodHandlerFactories = new ArrayList();
    private boolean             started                = false;

    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        MethodHandler result;
        Set           methodHandlers = new HashSet(methodHandlerFactories.size());
        Iterator      iterator       = methodHandlerFactories.iterator();

        while (iterator.hasNext())
        {
            MethodHandlerFactory factory       = (MethodHandlerFactory) iterator.next();
            MethodHandler        methodHandler = factory.createMethodHandler(methodKey);

            if (methodHandler != null)
            {
                methodHandlers.add(methodHandler);
            }
        }

        // --------------------------------------------------------------
        if (methodHandlers.size() == 0)
        {
            result = SilentMethodHandler.METHOD_HANDLER;
        }

        if (methodHandlers.size() == 1)
        {
            result = (MethodHandler) methodHandlers.iterator().next();
        }
        else
        {
            result = new ArrayMethodHandler(methodHandlers);
        }

        // --------------------------------------------------------------
        return result;
    }


    public void startup(RuntimeContext context) throws Exception {

        if (methodHandlerFactories.isEmpty())
        {
            LOG.error("CompositeMethodHandlerFactory does not have any child methodHandlerFactories configured");
        }

        Iterator iterator = methodHandlerFactories.iterator();

        while (iterator.hasNext())
        {
            MethodHandlerFactory factory = (MethodHandlerFactory) iterator.next();

            factory.startup(context);
        }

        started = true;
    }


    public void setMethodHandlerFactories(List methodHandlerFactories) {

        if (started)
        {
            throw new ConfigurationException("methodHandlerFactories can not be changed after startup");
        }

        Iterator iterator = methodHandlerFactories.iterator();

        while (iterator.hasNext())
        {
            Object element = iterator.next();

            if (element instanceof MethodHandlerFactory)
            {
                LOG.info("methodHandlerFactories contains '" + element + "'");
            }
            else
            {
                throw new ConfigurationException("all elements of 'methodHandlerFactories' mist implement '"
                                                 + MethodHandlerFactory.class.getName() + "'");
            }
        }

        this.methodHandlerFactories = methodHandlerFactories;
    }


    public void add(MethodHandlerFactory factory) {
        methodHandlerFactories.add(factory);
    }
}
