package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 */
public class ThreadYieldRunnable implements Runnable {

    public void run() {

        Thread.yield();

    }

    public String toString() {
        return "Thread.yield()";
    }
}
