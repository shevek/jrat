package org.shiftone.jrat.benchmark;


/**
 * @author Jeff Drost
 */
public class IncrementLongRunnable implements Runnable {

    private long i = 0;

    public void run() {
        i++;
    }

    public String toString() {
        return "increment a long";
    }
}