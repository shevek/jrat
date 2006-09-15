package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class IncrementIntegerRunnable implements Runnable {
	private int i = 0;

	public void run() {
		i++;
	}

	public String toString() {
		return "increment an int";
	}
}