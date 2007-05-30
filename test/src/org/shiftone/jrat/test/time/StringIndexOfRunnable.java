package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 *
 */
public class StringIndexOfRunnable implements Runnable {
	private final String string;

	public StringIndexOfRunnable() {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 100; i ++) {
			sb.append('X');
		}
		sb.append('Y');

		string = sb.toString();
	}


	public void run() {
		string.indexOf('Y');
	}

	public String toString() {
		return "string(length=101).indexOf('Y')";
	}

}
