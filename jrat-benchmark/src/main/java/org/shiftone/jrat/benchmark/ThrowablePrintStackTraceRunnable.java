package org.shiftone.jrat.benchmark;

import org.shiftone.jrat.util.io.nop.NullPrintWriter;


/**
 * @author Jeff Drost
 */
public class ThrowablePrintStackTraceRunnable implements Runnable {
    private Throwable throwable = new Throwable();

    public void run() {
        throwable.printStackTrace(NullPrintWriter.INSTANCE);
    }

    public String toString() {
        return "throwable.printStackTrace(/dev/null)";
    }
}
