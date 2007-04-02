package org.shiftone.jrat.core;



import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.SignatureUtil;
import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


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


    /**
     * A main runtime entry point.
     */
    public static MethodHandler getMethodHandler(Method method) {
        return getInternalHandler().getMethodHandler(method);
    }


    public static MethodHandler getMethodHandler(Constructor constructor) {
        return getInternalHandler().getMethodHandler(constructor);
    }


    /**
     * A main runtime entry point.
     */
    public static MethodHandler getMethodHandler(String className, Method method) {

        String methodName = method.getName();
        String signature  = SignatureUtil.getSignature(method);

        return getMethodHandler(className, methodName, signature);
    }
}
