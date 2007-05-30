package org.shiftone.jrat.test.time;

/**
 * @author Jeff Drost
 *
 */
public class StringRegionMatchesRunnable implements Runnable {

	private String stringA = "9876543210xxxxxxxxxx9876543210";
	private String stringB = "0123456789xxxxxxxxxx0123456789";

	public void run() {
		boolean matches = stringA.regionMatches(10, stringB, 10, 10);
	}

	public String toString() {
		return "stringA.regionMatches(10, stringB, 10, 10)";
	}

}
