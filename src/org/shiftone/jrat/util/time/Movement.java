package org.shiftone.jrat.util.time;


/**
 * @author Jeff Drost
 */
public interface Movement {
    long currentTimeNanos();

    void pauseTime();

    void resumeTime();
}
