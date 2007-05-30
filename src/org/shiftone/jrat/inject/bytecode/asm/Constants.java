package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;
import org.shiftone.jrat.inject.bytecode.InjectorStrategy;


/**
 * @author Jeff Drost
 */
public interface Constants {

    public static String initializeName = "$clinit" + InjectorStrategy.METHOD_POSTFIX;
    public static Method initialize = Method.getMethod("void " + initializeName + "()");
    public static String classInitName = "<clinit>";
    public static String classInitDesc = "()V";
    public static Method classInit = Method.getMethod("void " + classInitName + "()");

    public interface Throwable {
        public static Class CLASS = java.lang.Throwable.class;
        public static Type TYPE = Type.getType(CLASS);
    }

    public interface Clock {

        public static Class CLASS = org.shiftone.jrat.util.time.Clock.class;
        public static Type TYPE = Type.getType(CLASS);
        public static Method currentTimeNanos = Method.getMethod("long currentTimeNanos()");
    }

    public interface System {

        public static Class CLASS = java.lang.System.class;
        public static Type TYPE = Type.getType(CLASS);
        public static Method currentTimeMillis = Method.getMethod("long currentTimeMillis()");
    }

    public interface HandlerFactory {

        public static Class CLASS = org.shiftone.jrat.core.HandlerFactory.class;
        public static Type TYPE = Type.getType(CLASS);
        public static Method getMethodHandler =
                Method.getMethod(org.shiftone.jrat.core.spi.MethodHandler.class.getName()
                        + " getMethodHandler(String, String, String)");
    }

    public interface MethodHandler {

        public static Class CLASS = org.shiftone.jrat.core.spi.MethodHandler.class;
        public static Type TYPE = Type.getType(CLASS);
        public static Method onMethodStart = Method.getMethod("void onMethodStart(Object)");
        public static Method onMethodFinish = Method.getMethod("void onMethodFinish(Object, long, Throwable)");
    }
}
