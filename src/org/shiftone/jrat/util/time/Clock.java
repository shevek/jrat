package org.shiftone.jrat.util.time;



import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class Clock {

    private static final Logger   LOG = Logger.getLogger(Clock.class);
    private static final Movement MOVEMENT;
    private static final Class[]  NOARG_TYPES = {};

    static
    {
        MOVEMENT = createBestAvalibleMovement();
    }

    private static Movement createBestAvalibleMovement() {

        Movement movement = null;

        if (Settings.isNanoSecondTimingEnabled())
        {
            LOG.info("trying to use nanosecond timing resolution");

            try
            {
                Class systemClass = System.class;

                systemClass.getMethod("nanoTime", NOARG_TYPES);
                LOG.info("System.nanoTime() is available");

                movement = new SystemNanoTimeMovement();
            }
            catch (Throwable e)
            {
                LOG.debug("System.nanoTime() is not available");
            }


            if (movement == null) {

                try
                {
                    Class perfClass = Class.forName("sun.misc.Perf");

                    perfClass.getMethod("highResCounter", NOARG_TYPES);
                    perfClass.getMethod("highResFrequency", NOARG_TYPES);
                    LOG.info("sun.misc.Perf.highResCounter() and sun.misc.Perf.highResFrequency() are available");

                    movement = new SunMiscPerfMovement();
                }
                catch (Throwable e)
                {
                    LOG.debug("sun.misc.Perf timing is not available");
                }
            }
        }

        LOG.debug("using System.currentTimeNanos() for timing");
        if (movement == null) {
            movement = new SystemCurrentTimeMillisMovement();
        }

        if (false) {
            movement = new PauseMovement(movement);
        }

        return movement;
    }


    public static long currentTimeNanos() {
        return MOVEMENT.currentTimeNanos();
    }

    // todo - use this method
    public static void pauseTime() {
        MOVEMENT.pauseTime();
    }

    // todo - use this method
    public static void resumeTime() {
        MOVEMENT.resumeTime();
    }

    public static String getStrategyText() {
        return MOVEMENT.toString();
    }


}
