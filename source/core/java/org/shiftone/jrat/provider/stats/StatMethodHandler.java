package org.shiftone.jrat.provider.stats;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class StatMethodHandler extends MethodKeyAccumulator implements MethodHandler {

    private static final Logger LOG = Logger.getLogger(StatMethodHandler.class);

    public StatMethodHandler(MethodKey methodKey) {
        super(methodKey);
    }


    public void onMethodStart(Object obj) {
        onMethodStart();
    }


    public void onMethodFinish(Object obj, long durationNanos, Throwable throwable) {
        onMethodFinish(durationNanos, throwable == null);
    }

    /*
     * public String getClassName() { return methodKey.getClassName(); } public
     * String getMethodName() { return methodKey.getMethodName(); } public
     * String getSignature() { return methodKey.getSignature(); }
     */
}
