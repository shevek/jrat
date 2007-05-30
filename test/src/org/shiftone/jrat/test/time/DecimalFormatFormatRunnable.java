package org.shiftone.jrat.test.time;

import java.text.DecimalFormat;


/**
 * @author Jeff Drost
 *
 */
public class DecimalFormatFormatRunnable implements Runnable {
	private DecimalFormat decimalFormat = new DecimalFormat();
	private double decimal = 123456789.987654321;

	public void run() {
		decimalFormat.format(decimal);
	}

	public String toString() {
		return "decimalFormat.format(decimal)";
	}
}
