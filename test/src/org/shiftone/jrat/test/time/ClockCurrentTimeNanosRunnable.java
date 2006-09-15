package org.shiftone.jrat.test.time;

import org.shiftone.jrat.util.time.Clock;

/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class ClockCurrentTimeNanosRunnable implements Runnable {

	public void run() {
		Clock.currentTimeNanos();
	}

	public String toString() {
		return "Clock.currentTimeNanos()";
	}

}
