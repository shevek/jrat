package org.shiftone.jrat.util.time;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.9 $
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
