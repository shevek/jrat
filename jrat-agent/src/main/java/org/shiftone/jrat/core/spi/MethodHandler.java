package org.shiftone.jrat.core.spi;

import org.shiftone.jrat.core.ThreadState;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface MethodHandler {

    void onMethodStart();


    void onMethodFinish(long durationMillis, Throwable throwable);
}
