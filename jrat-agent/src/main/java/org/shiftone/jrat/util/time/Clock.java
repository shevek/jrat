package org.shiftone.jrat.util.time;


import org.shiftone.jrat.util.log.Logger;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Clock {

    private static final Logger LOG = Logger.getLogger(Clock.class);
    long offset = 0;


    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long now() {
        return currentTimeMillis() - offset;
    }

    public void pauseTime() {

        // MOVEMENT.pauseTime();
    }

    public static void resumeTime() {
        //MOVEMENT.resumeTime();
    }


}