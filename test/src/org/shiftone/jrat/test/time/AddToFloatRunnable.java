package org.shiftone.jrat.test.time;

/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class AddToFloatRunnable implements Runnable {

	private float f = 0f;

	public void run() {
		f += (float) 1.1f;
	}

	public String toString() {
		return "add to a float";
	}
}