package org.shiftone.jrat.util.time;


import org.shiftone.jrat.util.log.Logger;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Clock {

    private static final Logger LOG = Logger.getLogger(Clock.class);

    private static final Class[] NOARG_TYPES = {};

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    // todo - use this method
    public static void pauseTime() {
        // MOVEMENT.pauseTime();
    }

    // todo - use this method
    public static void resumeTime() {
        //MOVEMENT.resumeTime();
    }


}
