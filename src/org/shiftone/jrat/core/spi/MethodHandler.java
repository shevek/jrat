package org.shiftone.jrat.core.spi;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.11 $
 */
public interface MethodHandler {

    void onMethodStart(Object obj);


    void onMethodFinish(Object target, long durationNanos, Throwable throwable);
}
