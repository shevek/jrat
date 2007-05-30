package org.shiftone.jrat.provider.rate2;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;


/**
 * @author Jeff Drost
 */
public class RateMethodHandlerFactory implements MethodHandlerFactory {

    private Context context;

    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        MethodHandler methodHandler = new RateMethodHandler();

        if (true) {
            methodHandler = new BoundaryMethodHandler(context, methodHandler);
        }

        return methodHandler;
    }


    public void startup(RuntimeContext context) throws Exception {
    }
}
