package org.shiftone.jrat.test.time;


/**
 * @author Jeff Drost
 */
public class ExceptionNewRunnable implements Runnable {

    public void run() {
        new Exception();
    }

    public String toString() {
        return "new Exception()";
    }
}
