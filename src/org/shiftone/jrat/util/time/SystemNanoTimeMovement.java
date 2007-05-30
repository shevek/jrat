package org.shiftone.jrat.util.time;



/**
 * @author Jeff Drost
 *
 */
public class SystemNanoTimeMovement implements Movement {

    public long currentTimeNanos() {
        return System.nanoTime();
    }

    public void pauseTime() {}
    public void resumeTime() {}

    public String toString() {
        return "(System.nanoTime())";
    }
}
