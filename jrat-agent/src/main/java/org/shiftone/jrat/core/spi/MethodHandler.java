package org.shiftone.jrat.core.spi;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface MethodHandler {

    void onMethodStart();

    void onMethodFinish(long durationMillis, Throwable throwable);
}
