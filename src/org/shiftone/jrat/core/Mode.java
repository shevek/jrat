package org.shiftone.jrat.core;

import org.shiftone.jrat.util.log.Logger;

/**
 * This has a bit of a smell to it.
 * The problem is the system needs to know how it's being executed.  In the case of
 * runtime, it needs to read (and possibly create) files.  In the case of desktop, that
 * would be silly.
 * The mode can be set multiple times, but once it's read it can not be set.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Mode {

    private static final Logger LOG = Logger.getLogger(Mode.class);

    public static final Mode UNKNOWN = new Mode("unknown", false, 0);
    public static final Mode DESKTOP = new Mode("desktop", false, 1);
    public static final Mode RUNTIME = new Mode("runtime", true, 2);

    private static Mode current = UNKNOWN;
    private static boolean locked = false;

    private String name;
    private boolean environmentLoadingEnabled;
    private int priority;


    public Mode(String name, boolean environmentLoadingEnabled, int priority) {
        this.name = name;
        this.environmentLoadingEnabled = environmentLoadingEnabled;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public boolean isEnvironmentLoadingEnabled() {
        return environmentLoadingEnabled;
    }

    // todo - fix this
    public static void set(Mode newMode) {

        LOG.info("set " + newMode);
        if (current.priority >= newMode.priority) {

            return;

        } else if (locked) {

            throw new IllegalStateException("mode is locked");

        } else {

            if (newMode.priority > current.priority) {
                // the mode can be changed to a higher priority mode
                current = newMode;
            }
            
        }

    }

    public static Mode get() {
        //LOG.info("get",new Throwable());
        if (!locked) {
            LOG.info("locking");
            locked = true;
        }
        return current;
    }


    public String toString() {
        return "Mode[" + name + "]";
    }
}
