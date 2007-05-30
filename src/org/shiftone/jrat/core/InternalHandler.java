package org.shiftone.jrat.core;


import org.shiftone.jrat.core.boot.Environment;
import org.shiftone.jrat.core.config.CacheMethodHandlerFactory;
import org.shiftone.jrat.core.config.ConfigMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.Method;


/**
 * This class basically does all the work for the Factory. It exists to
 * allow some singletons to be initialized the first time someone this class is
 * loaded. In contrast, this class should only be loaded when a call is made to
 * the Factory's getMethodHandler method is called. There is very little
 * difference between this approach and implementing lazy loading.
 *
 * @author Jeff Drost
 */
public class InternalHandler {

    private static final Logger LOG = Logger.getLogger(InternalHandler.class);


    private MethodHandlerFactory rootHandlerFactory;


    /**
     * Constructor for InternalHandler
     */
    public InternalHandler() {


        try {

            rootHandlerFactory = new CacheMethodHandlerFactory(
                    new ConfigMethodHandlerFactory(
                            Environment.INSTANCE.getConfiguration()
                    )
            );

            rootHandlerFactory.startup(new RuntimeContextImpl());

        } catch (Exception e) {

            rootHandlerFactory = SilentMethodHandler.HANDLER_FACTORY;

            LOG.error("There was an error starting up a handler factory", e);
            LOG.info("JRat will ignore all configuration and use the slient handler");
        }
    }


    public final synchronized MethodHandler getMethodHandler(MethodKey methodKey) {
        try {

            return rootHandlerFactory.createMethodHandler(methodKey);

        } catch (Throwable e) {

            LOG.error("failed to create handler for " + methodKey, e);
            return SilentMethodHandler.METHOD_HANDLER;

        }
    }


    public final synchronized MethodHandler getMethodHandler(Method method) {

        MethodHandler methodHandler = null;
//        MethodWrapper key = new MethodWrapper(method);
//
//        methodHandler = (MethodHandler) methodCache.get(key);
//
//        if (methodHandler == null) {
//            String className = method.getDeclaringClass().getName();
//            String methodName = method.getName();
//            String signature = SignatureUtil.getSignature(method);
//
//            methodHandler = getMethodHandler(new MethodKey(className, methodName, signature));
//
//            methodCache.put(key, methodHandler);
//        }

        return methodHandler;
    }


}
