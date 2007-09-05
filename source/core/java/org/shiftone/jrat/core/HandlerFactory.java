package org.shiftone.jrat.core;


import org.shiftone.jrat.core.config.CacheMethodHandlerFactory;
import org.shiftone.jrat.core.config.ConfigMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.log.Logger;


public class HandlerFactory {

    private static final Logger LOG = Logger.getLogger(HandlerFactory.class);
    
    /**
     * Force initialization.  This should really only be called from test cases to
     * initialize a particular configuration.
     */
    public static void initialize() {
        Singleton.initialize();
    }


    private static class Singleton {

        private static MethodHandlerFactory rootHandlerFactory;

        static {
            
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

        public static void initialize() {
        }

        public static synchronized MethodHandler getMethodHandler(MethodKey methodKey) {
            try {
                return rootHandlerFactory.createMethodHandler(methodKey);
            } catch (Exception e) {
                LOG.error("failed to column MethodHandler for : " + methodKey, e);
                return SilentMethodHandler.METHOD_HANDLER;
            }
        }
    }


    public static synchronized MethodHandler getMethodHandler(String className, String methodName, String signature) {
        return Singleton.getMethodHandler(MethodKey.getInstance(className, methodName, signature));
    }


    public static synchronized MethodHandler getMethodHandler(Class klass, String methodName, String signature) {
        return Singleton.getMethodHandler(MethodKey.getInstance(klass.getName(), methodName, signature));
    }


}
