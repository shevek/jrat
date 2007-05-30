package org.shiftone.jrat.core.spi;


import org.shiftone.jrat.util.time.TimeUnit;


/**
 * This is a really fast/easy way to port an older MethodHandler to the new interface.
 * Extend this class.
 *
 * @author Jeff Drost
 */
public abstract class LegacyMethodHandler implements MethodHandler {

    protected abstract void onMethodStart(Object obj, Object[] params);


    protected abstract void onMethodFinish(Object obj, Object[] params, Object ret, long duration, boolean success);


    protected abstract void onMethodError(Object obj, Object[] params, Throwable throwable);


    public void onMethodStart(Object target) {
        onMethodStart(target, null);
    }


    public void onMethodFinish(Object target, long durationNanos, Throwable throwable) {

        if (throwable != null) {
            onMethodError(target, null, throwable);
        }

        onMethodFinish(target, null, null, TimeUnit.MS.fromNanos(durationNanos), throwable == null);
    }
}
