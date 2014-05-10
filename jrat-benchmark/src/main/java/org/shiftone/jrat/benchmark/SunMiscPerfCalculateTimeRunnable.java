package org.shiftone.jrat.benchmark;

import sun.misc.Perf;


/**
 * @author Jeff Drost
 */
public class SunMiscPerfCalculateTimeRunnable implements Runnable {
    static Perf perf = Perf.getPerf();

    public void run() {
        long counter = perf.highResCounter();
        long frequency = perf.highResFrequency();

        long ns = (counter * 1000000000 / frequency);
    }

    public String toString() {
        return "sun.misc.Perf (highResCounter * 1000000000 / highResFrequency)";
    }
}
