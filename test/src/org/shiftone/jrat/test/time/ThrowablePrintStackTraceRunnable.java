package org.shiftone.jrat.test.time;

import org.shiftone.jrat.util.io.nop.NullPrintWriter;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
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
