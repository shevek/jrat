package org.shiftone.jrat.benchmark;

import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.Method;

/**
 * @author Jeff Drost
 */
public class MethodReflectCallRunnable implements Runnable {
    private static final Logger LOG = Logger.getLogger(MethodReflectCallRunnable.class);
    private Object[] args = {};
    private Method method;

    MethodReflectCallRunnable() {
        try {
            method = MethodReflectCallRunnable.class.getMethod("doIt", new Class[]{});
        } catch (Exception e) {
            throw new JRatException("unable to get method", e);
        }
    }

    public void doIt() {
        ;
    }

    public void run() {

        try {
            method.invoke(this, args);
        } catch (Exception e) {
            throw new JRatException("unable to get method", e);
        }

    }

    public String toString() {
        return "method.invoke(this, args)";
    }
}
