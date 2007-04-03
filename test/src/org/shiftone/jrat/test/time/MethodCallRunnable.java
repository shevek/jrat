package org.shiftone.jrat.test.time;

import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public class MethodCallRunnable implements Runnable {
	private static final Logger LOG = Logger.getLogger(MethodCallRunnable.class);

	public void doIt() {
		;
	}


	public void run() {
		doIt();
	}

		public String toString() {
		return "doIt() - call to empty method";
	}
}
