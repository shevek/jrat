package org.shiftone.jrat.api;


import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.time.Clock;

import java.lang.reflect.Method;


/**
 * @author Jeff Drost
 */
public class Monitor {

    private final MethodHandler handler;

    public Monitor(MethodHandler handler) {
        this.handler = handler;
    }

//    public Monitor(Method method) {
//        this.handler = HandlerFactory.getMethodHandler(method);
//    }


    public Monitor(String className, String methodName, String signature) {
        this.handler = HandlerFactory.getMethodHandler(className, methodName, signature);
    }


    public Monitor(Class klass, String methodName, String signature) {
        this.handler = HandlerFactory.getMethodHandler(klass.getName(), methodName, signature);
    }


    public Monitor(Class klass, String methodName) {
        this.handler = HandlerFactory.getMethodHandler(klass.getName(), methodName, "()V");
    }


    public Object execute(Object instance, Command command) throws Throwable {

        try {
            return execute(handler, instance, command);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public Object execute(Command command) {

        try {
            return execute(handler, null, command);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Object execute(Method method, Object instance, Command command) throws Throwable {
       // return execute(HandlerFactory.getMethodHandler(method), instance, command);
        return null;
    }


    public static Object execute(MethodHandler handler, Object instance, Command command) throws Throwable {

        handler.onMethodStart(instance);

        long startTime = Clock.currentTimeNanos();

        try {
            Object result = command.execute();

            handler.onMethodFinish(instance, Clock.currentTimeNanos() - startTime, null);

            return result;
        }
        catch (Throwable throwable) {
            handler.onMethodFinish(instance, Clock.currentTimeNanos() - startTime, throwable);

            throw throwable;
        }
    }


    private static void main(String[] args) {

        Monitor monitor = new Monitor(Monitor.class, "foo");

        monitor.execute(new Callable() {

            public void run() {

                // do some work here...
            }
        });
    }
}
