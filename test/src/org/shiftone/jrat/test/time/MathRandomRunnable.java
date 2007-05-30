package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 */
public class MathRandomRunnable implements Runnable {

    private long i = 0;

    public void run() {
        Math.random();
    }

    public String toString() {
        return "Math.random()";
    }
}