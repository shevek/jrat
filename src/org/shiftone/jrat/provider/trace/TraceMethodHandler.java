package org.shiftone.jrat.provider.trace;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.6 $
 */
public class TraceMethodHandler implements MethodHandler {

    private static final Logger             LOG = Logger.getLogger(TraceMethodHandler.class);
    private final TraceMethodHandlerFactory factory;
    private final MethodKey                 methodKey;
    private AtomicLong                      callCount = new AtomicLong();

    public TraceMethodHandler(MethodKey methodKey, TraceMethodHandlerFactory factory) {
        this.methodKey = methodKey;
        this.factory   = factory;
    }


    public void onMethodStart(Object target) {
        factory.getDelegate().onMethodStart(methodKey, callCount.incrementAndGet());
    }


    public void onMethodFinish(Object target, long durationNanos, Throwable throwable) {
        factory.getDelegate().onMethodFinish(durationNanos, throwable);
    }
}
