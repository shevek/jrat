package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class SystemNanoTimeRunnable implements Runnable {

	public void run() {
		System.nanoTime();
	}

	public String toString() {
		return "System.nanoTime()";
	}
}
