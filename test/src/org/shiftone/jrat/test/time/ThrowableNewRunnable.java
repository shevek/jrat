package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class ThrowableNewRunnable implements Runnable {

	public void run() {
		new Throwable();
	}

	public String toString() {
		return "new Throwable()";
	}
}
