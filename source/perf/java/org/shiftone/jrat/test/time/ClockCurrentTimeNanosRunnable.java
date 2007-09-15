package org.shiftone.jrat.test.time;

import org.shiftone.jrat.util.time.Clock;

/**
 * @author Jeff Drost
 */
public class ClockCurrentTimeNanosRunnable implements Runnable {

    public void run() {
        Clock.currentTimeMillis();
    }

    public String toString() {
        return "Clock.currentTimeMillis()";
    }

}
