package org.shiftone.jrat.core;



import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;


/**
 * Factory creates the root level handler factory.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.19 $
 */
public class RootFactory {

    private static final Logger LOG = Logger.getLogger(RootFactory.class);

    public MethodHandlerFactory getHandlerFactory() {

        MethodHandlerFactory factory      = null;
        String               factoryClass = Settings.getHandlerFactoryClassName();

        LOG.info("JRat creating root handler factory...");

        try
        {
            if (factoryClass != null)
            {
                factory = (MethodHandlerFactory) ResourceUtil.newInstance(factoryClass);
            }
        }
        catch (Throwable e)
        {
            LOG.error("error configuring JRat", e);
        }

        if (factory == null)
        {
            LOG.info("reverting to silent handler");

            return InternalHandler.DEFAULT_HANDLER_FACTORY;
        }
        else
        {
            return factory;
        }
    }
}
