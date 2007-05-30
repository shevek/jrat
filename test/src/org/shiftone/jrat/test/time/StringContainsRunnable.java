package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 *
 */
public class StringContainsRunnable implements Runnable {
	private final String string;

	public StringContainsRunnable() {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 100; i ++) {
			sb.append('X');
		}
		sb.append('Y');

		string = sb.toString();
	}


	public void run() {
		string.contains("XXXXY");
	}

	public String toString() {
		return "string(length=101).indexOf(\"XXXXY\")";
	}

}
