package org.shiftone.jrat.test.time;

/**
 * @author Jeff Drost
 * @version $Revision: 1.3 $
 */
public class SystemCurrentTimeMillisRunnable implements Runnable {

	public void run() {
		System.currentTimeMillis();
	}

	public String toString() {
		return "System.currentTimeMillis()";
	}
}