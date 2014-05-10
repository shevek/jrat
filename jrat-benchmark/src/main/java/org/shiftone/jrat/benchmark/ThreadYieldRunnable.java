package org.shiftone.jrat.benchmark;


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
