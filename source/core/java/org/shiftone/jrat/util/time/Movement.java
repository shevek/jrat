package org.shiftone.jrat.util.time;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface Movement {
    long currentTimeNanos();

    void pauseTime();

    void resumeTime();
}
