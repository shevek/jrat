package org.shiftone.jrat.util.time;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.7 $
 */
public interface Movement {
    long currentTimeNanos();
    void pauseTime();
    void resumeTime();
}
