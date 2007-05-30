package org.shiftone.jrat.provider.rate;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class RateMethodHandler implements MethodHandler {

    private static final Logger LOG                = Logger.getLogger(RateMethodHandler.class);
    private MethodKey           methodKey          = null;
    private Accumulator         currentAccumulator = new Accumulator();

    RateMethodHandler(MethodKey methodKey) {
        this.methodKey = methodKey;
    }


    public synchronized void onMethodFinish(Object obj, long durationNanos, Throwable throwable) {
        currentAccumulator.onMethodFinish(durationNanos, throwable == null);
    }


    public synchronized void onMethodStart(Object obj) {
        currentAccumulator.onMethodStart();
    }


    public synchronized Accumulator getAndReplaceAccumulator() {

        Accumulator newAccumulator = new Accumulator();
        Accumulator oldAccumulator = currentAccumulator;

        currentAccumulator = newAccumulator;

        return oldAccumulator;
    }


    public MethodKey getMethodKey() {
        return methodKey;
    }
}
