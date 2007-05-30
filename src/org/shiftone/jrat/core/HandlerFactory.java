package org.shiftone.jrat.core;


import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;


/**
 * This class temporary. It's purpose is to smooth the change of the
 * MethodHandler interface.
 */
public class HandlerFactory {

    private static final Logger    LOG             = Logger.getLogger(HandlerFactory.class);
    private static InternalHandler internalHandler = null;

    private static synchronized InternalHandler getInternalHandler() {

        if (internalHandler == null)
        {
            LOG.info("new InternalHandler");

            internalHandler = new InternalHandler();
        }

        return internalHandler;
    }


	/**
	 * Force initialization.  This should really only be called from test cases to
	 * initialize a particular configuration.
	 *
	 */
	public static void initialize() {
		getInternalHandler();
	}

	/**
     * A main runtime entry point.
     */
    public static synchronized MethodHandler getMethodHandler(MethodKey methodKey) {
        return getInternalHandler().getMethodHandler(methodKey);
    }


    /**
     * A main runtime entry point.
     */
    public static synchronized MethodHandler getMethodHandler(String className, String methodName, String signature) {
        return getMethodHandler(new MethodKey(className, methodName, signature));
    }


    public static synchronized MethodHandler getMethodHandler(Class klass, String methodName, String signature) {
        return getMethodHandler(new MethodKey(klass.getName(), methodName, signature));
    }


 
     
}
