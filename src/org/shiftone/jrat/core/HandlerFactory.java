package org.shiftone.jrat.core;


import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.config.CacheMethodHandlerFactory;
import org.shiftone.jrat.core.config.ConfigMethodHandlerFactory;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;


public class HandlerFactory {

    private static final Logger LOG = Logger.getLogger(HandlerFactory.class);
    private static MethodHandlerFactory rootHandlerFactory;

    private static synchronized MethodHandlerFactory getInternalHandler() {

        if (rootHandlerFactory == null) {

            Mode.set(Mode.RUNTIME);

            try {

                rootHandlerFactory = new CacheMethodHandlerFactory(
                        new ConfigMethodHandlerFactory(
                                Environment.getConfiguration()
                        )
                );

                rootHandlerFactory.startup(new RuntimeContextImpl());

            } catch (Exception e) {

                rootHandlerFactory = SilentMethodHandler.HANDLER_FACTORY;

                LOG.error("There was an error starting up a handler factory", e);
                LOG.info("JRat will ignore all configuration and use the slient handler");
            }
        }

        return rootHandlerFactory;
    }


    /**
     * Force initialization.  This should really only be called from test cases to
     * initialize a particular configuration.
     */
    public static void initialize() {
        getInternalHandler();
    }

    /**
     * A main runtime entry point.
     */
    public static synchronized MethodHandler getMethodHandler(MethodKey methodKey) {
        try {
            return getInternalHandler().createMethodHandler(methodKey);
        } catch (Exception e) {
            LOG.error("failed to create MethodHandler for : " + methodKey, e);
            return  SilentMethodHandler.METHOD_HANDLER;
        }
    }


    /**
     * A main runtime entry point.
     */
    public static synchronized MethodHandler getMethodHandler(String className, String methodName, String signature) {
        return getMethodHandler(MethodKey.create(className, methodName, signature));
    }


    public static synchronized MethodHandler getMethodHandler(Class klass, String methodName, String signature) {
        return getMethodHandler(MethodKey.create(klass.getName(), methodName, signature));
    }


}
