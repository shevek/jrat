package org.shiftone.jrat.provider.errors;


import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.log.Logger;

import java.io.PrintWriter;
import java.util.Date;


/**
 * @author Jeff Drost
 */
public class ErrorsMethodHandler implements MethodHandler {

    private static final Logger LOG = Logger.getLogger(ErrorsMethodHandler.class);
    private PrintWriter printWriter;
    private AtomicLong errorSequence = new AtomicLong();

    public ErrorsMethodHandler(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }


    public void onMethodStart(Object obj) {
    }


    public synchronized void onMethodFinish(Object obj, long durationNanos, Throwable throwable) {

        if (throwable != null) {
            printWriter.println("***** Error #" + errorSequence.incrementAndGet() + " *****");
            printWriter.println(new Date());
            printWriter.println(obj.getClass().getName());
            printWriter.println(obj.toString());
            printWriter.println();
            printWriter.println(throwable.getMessage());
            throwable.printStackTrace(printWriter);
            printWriter.println();
        }
    }
}
