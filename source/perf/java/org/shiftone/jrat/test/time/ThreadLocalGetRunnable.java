package org.shiftone.jrat.test.time;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ThreadLocalGetRunnable implements Runnable {

    public static final ThreadLocal STATE = new ThreadLocal() {

        public Object get() {
            return "this is a test";
        }
    };


    public void run() {
        STATE.get();        
    }

    public String toString() {
        return "ThreadLocal.get()";
    }
}
