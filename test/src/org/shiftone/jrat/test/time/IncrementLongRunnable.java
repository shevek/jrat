package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class IncrementLongRunnable implements Runnable {

	private long i = 0;

	public void run() {
		i++;
	}

	public String toString() {
		return "increment a long";
	}
}