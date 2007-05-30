package org.shiftone.jrat.core.spi;



/**
 * @author Jeff Drost
 *
 */
public interface MethodHandler {

    void onMethodStart(Object obj);


    void onMethodFinish(Object target, long durationNanos, Throwable throwable);
}
