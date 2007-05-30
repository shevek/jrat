package org.shiftone.jrat.util.time;


/**
 * @author Jeff Drost
 */
public class SystemCurrentTimeMillisMovement implements Movement {

    public long currentTimeNanos() {
        return 1000000 * System.currentTimeMillis();
    }

    public void pauseTime() {
    }

    public void resumeTime() {
    }

    public String toString() {
        return "(1,000,000 * System.currentTimeMillis())";
    }
}
