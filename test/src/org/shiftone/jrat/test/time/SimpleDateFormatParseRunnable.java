package org.shiftone.jrat.test.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * @author Jeff Drost
 *
 */
public class SimpleDateFormatParseRunnable implements Runnable {
	private String source = "2001-07-04T12:08:56.235-0700";
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public void run() {
		try {
			format.parse(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return "simpleDateFormat.parse(date)";
	}
}
