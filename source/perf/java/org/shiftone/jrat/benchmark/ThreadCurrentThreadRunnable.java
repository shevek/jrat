package org.shiftone.jrat.benchmark;


/**
 * @author Jeff Drost
 */
public class ThreadCurrentThreadRunnable implements Runnable {

    public void run() {
        Thread.currentThread();
    }

    public String toString() {
        return "Thread.currentThread()";
    }
}
