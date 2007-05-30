package org.shiftone.jrat.test.time;

/**
 * @author Jeff Drost
 *
 */
public class ThreadSleep0Runnable implements Runnable {

	public void run() {
		try {
			Thread.sleep(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return "Thread.sleep(0)";
	}
}
