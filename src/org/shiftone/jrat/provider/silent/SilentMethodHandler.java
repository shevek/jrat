package org.shiftone.jrat.provider.silent;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;


/**
 * Class SilentMethodHandler
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.19 $
 */
public class SilentMethodHandler implements MethodHandler, MethodHandlerFactory {

    private static final SilentMethodHandler INSTANCE        = new SilentMethodHandler();
    public static final MethodHandlerFactory HANDLER_FACTORY = INSTANCE;
    public static final MethodHandler        METHOD_HANDLER  = INSTANCE;

    public void onMethodError(Object obj, Object[] args, Throwable throwable) {}


    public void onMethodFinish(Object obj, long durationNanos, Throwable throwable) {}


    public void onMethodStart(Object obj) {}


    public MethodHandler createMethodHandler(MethodKey methodKey) {
        return this;
    }


    public void startup(RuntimeContext context) {}


    public void shutdown() {}
}
