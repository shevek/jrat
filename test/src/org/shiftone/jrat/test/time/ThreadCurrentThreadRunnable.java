package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 *
 */
public class ThreadCurrentThreadRunnable implements Runnable {

	public void run() {
		Thread.currentThread();
	}

	public String toString() {
		return "Thread.currentThread()";
	}
}
