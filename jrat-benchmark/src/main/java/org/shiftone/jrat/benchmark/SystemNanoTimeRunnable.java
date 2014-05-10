package org.shiftone.jrat.benchmark;


/**
 * @author Jeff Drost
 */
public class SystemNanoTimeRunnable implements Runnable {

    public void run() {
        System.nanoTime();
    }

    public String toString() {
        return "System.nanoTime()";
    }
}
