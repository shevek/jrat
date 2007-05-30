package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 *
 */
public class StringConcatRunnable implements Runnable {
	private String a = "do not be alarmed. ";
	private String b = "this is only";

	public void run() {
		String c = a + b;
	}

	public String toString() {
		return "concat two strings";
	}
}
