package org.shiftone.jrat.provider.rate2;



import org.shiftone.jrat.core.spi.MethodHandler;


/**
 * @author Jeff Drost
 *
 */
public class BoundaryMethodHandler implements MethodHandler {

    private MethodHandler methodHandler;
    private Context       context;

    public BoundaryMethodHandler(Context context, MethodHandler methodHandler) {
        this.context       = context;
        this.methodHandler = methodHandler;
    }


    public void onMethodStart(Object obj) {

        int depth = context.delta(+1);

        if (depth == 0)
        {
            methodHandler.onMethodStart(obj);
        }
    }


    public void onMethodFinish(Object target, long durationNanos, Throwable throwable) {

        int depth = context.delta(-1);

        if (depth == 1)
        {
            methodHandler.onMethodFinish(target, durationNanos, throwable);
        }
    }
}
