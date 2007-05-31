package org.shiftone.jrat.provider.rate;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;


/**
 * Class RateMethodHandlerFactory
 *
 * @author Jeff Drost
 */
public class RateMethodHandlerFactory extends AbstractMethodHandlerFactory {

    private static final Logger LOG = Logger.getLogger(RateMethodHandlerFactory.class);
    private static final Runtime RT = Runtime.getRuntime();
    private Timer timer = new Timer(true);
    private RateTimerTask task = new RateTimerTask(this);
    private RateMethodHandler[] handlers = null;
    private RateOutput output = null;
    private boolean startupSucceeded = false;
    private int handlerMax = 100;
    private long period = 1000 * 10;

    /**
     * Method createMethodHandler
     *
     * @param methodKey .
     * @return .
     */
    public synchronized MethodHandler createMethodHandler(MethodKey methodKey) {

        MethodHandler handler = null;
        int methodCount = output.getMethodCount();

        if (startupSucceeded == false) {
            LOG.info("startup failed, returning null handler");
        } else if (methodCount >= handlerMax) {
            LOG.info("handler max (" + handlerMax + ") exceeded, returning null handler");
        } else {
            handler = handlers[methodCount] = new RateMethodHandler(methodKey);

            try {
                output.printMethodDef(methodKey);
            }
            catch (Exception e) {
                LOG.error("error writing method def : " + methodKey, e);
            }
        }

        return handler;
    }


    /**
     * Method startup
     *
     * @param context .
     */
    public void startup(RuntimeContext context) throws Exception {

        super.startup(context);

        OutputStream outputStream = null;

        this.handlers = new RateMethodHandler[handlerMax];

        try {
            outputStream = context.createOutputStream(getOutputFile());
            output = new RateOutput(outputStream, handlerMax, context);

            output.printHeader(period);
            context.registerShutdownListener(this);
            timer.schedule(task, period, period);

            startupSucceeded = true;
        }
        catch (Exception e) {
            output.close();

            throw e;
        }
    }


    /**
     * method
     */
    public void writeSample() throws IOException {
        output.printSample(handlers);
    }


    /**
     * Method shutdown
     */
    public void shutdown() {

        MethodKey methodKey = null;

        timer.cancel();
        task.cancel();

        try {
            output.close();
        }
        catch (Exception e) {
            LOG.error("error closing output", e);
        }
    }


    public String toString() {
        return "Rate Handler Factory";
    }
}
