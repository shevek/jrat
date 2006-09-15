package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class ExceptionNewRunnable implements Runnable {

	public void run() {
		new Exception();
	}

	public String toString() {
		return "new Exception()";
	}
}
