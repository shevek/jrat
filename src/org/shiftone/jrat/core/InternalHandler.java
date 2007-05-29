package org.shiftone.jrat.core;



import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.MethodWrapper;
import org.shiftone.jrat.util.SignatureUtil;
import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.Hashtable;
import java.util.Map;


/**
 * This class basically does all the work for the Factory. It exists to
 * allow some singletons to be initialized the first time someone this class is
 * loaded. In contrast, this class should only be loaded when a call is made to
 * the Factory's getMethodHandler method is called. There is very little
 * difference between this approach and implementing lazy loading.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.26 $
 */
public class InternalHandler {

    private static final Logger              LOG                     = Logger.getLogger(InternalHandler.class);
    public static final MethodHandlerFactory DEFAULT_HANDLER_FACTORY = SilentMethodHandler.HANDLER_FACTORY;
    public static final MethodHandler        DEFAULT_METHOD_HANDLER  = SilentMethodHandler.METHOD_HANDLER;
    private MethodHandlerFactory             rootHandlerFactory;
    private final RuntimeContext             runtimeContext;
    private final RootFactory                rootFactory;
    private final Map                        handlerCache;
    private final Map                        methodCache;

    /**
     * Constructor for InternalHandler
     */
    public InternalHandler() {

        runtimeContext     = new RuntimeContextImpl();
        rootFactory        = new RootFactory();
        handlerCache       = new Hashtable();
        methodCache        = new Hashtable();
        rootHandlerFactory = rootFactory.getHandlerFactory();

        try
        {
            rootHandlerFactory.startup(runtimeContext);
        }
        catch (Exception e)
        {
            rootHandlerFactory = DEFAULT_HANDLER_FACTORY;

            LOG.error("There was an error starting up a handler factory", e);
            LOG.info("JRat will ignore all configuration and use the slient handler");
        }
    }


    public final synchronized MethodHandler getMethodHandler(MethodKey methodKey) {

        MethodHandler methodHandler = null;

        methodHandler = (MethodHandler) handlerCache.get(methodKey);

        if (methodHandler == null)
        {
            try
            {
                methodHandler = rootHandlerFactory.createMethodHandler(methodKey);
            }
            catch (Throwable e)
            {
                LOG.error("createMethodHandler() failed on " + rootHandlerFactory + " returning NOP handler", e);
            }

            if (methodHandler == null)
            {
                methodHandler = DEFAULT_METHOD_HANDLER;
            }

            handlerCache.put(methodKey, methodHandler);
        }

        return methodHandler;
    }


    public final synchronized MethodHandler getMethodHandler(Method method) {

        MethodHandler methodHandler = null;
        MethodWrapper key           = new MethodWrapper(method);

        methodHandler = (MethodHandler) methodCache.get(key);

        if (methodHandler == null)
        {
            String className  = method.getDeclaringClass().getName();
            String methodName = method.getName();
            String signature  = SignatureUtil.getSignature(method);

            methodHandler = getMethodHandler(new MethodKey(className, methodName, signature));

            methodCache.put(key, methodHandler);
        }

        return methodHandler;
    }


    public final synchronized MethodHandler getMethodHandler(Constructor constructor) {

        MethodHandler methodHandler = null;

        methodHandler = (MethodHandler) methodCache.get(constructor);

        if (methodHandler == null)
        {
            String className  = constructor.getDeclaringClass().getName();
            String methodName = constructor.getName();
            String signature  = SignatureUtil.getSignature(constructor);

            methodHandler = getMethodHandler(new MethodKey(className, methodName, signature));

            methodCache.put(constructor, methodHandler);
        }

        return methodHandler;
    }
}
