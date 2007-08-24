package org.shiftone.jrat.util.time;


import sun.misc.Perf;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SunMiscPerfMovement implements Movement {

    private static final Perf perf = Perf.getPerf();
    private static final long FREQUENCY = perf.highResFrequency();

    public long currentTimeNanos() {
        return (perf.highResCounter() * 1000000000 / FREQUENCY);
    }

    public void pauseTime() {
    }

    public void resumeTime() {
    }

    public String toString() {
        return "(perf.highResCounter() * 1,000,000,000 / perf.highResFrequency())";
    }
}
