package org.shiftone.jrat.test.time;

/**
 * @author Jeff Drost
 *
 */
public class SystemCurrentTimeMillisRunnable implements Runnable {

	public void run() {
		System.currentTimeMillis();
	}

	public String toString() {
		return "System.currentTimeMillis()";
	}
}