package org.shiftone.jrat.util.log;


import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.target.*;

import java.io.PrintWriter;


/**
 * There are currently 3 ways that logging can be configured...
 * <li>turned off - using NullLogTarget
 * <li>logging to a PrintWriter - System.out or file
 * <li>using thread based logging - each thread can have it's own LogTarget
 * (this is for the Desktop)
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class LoggerFactory implements Constants {

    private static final NullLogTarget NULL_LOG_TARGET = NullLogTarget.INSTANCE;
    private static final WriterLogTarget SYSTEM_OUT_TARGET = new WriterLogTarget(System.out);
    private static final ProxyLogTarget PROXY_LOG_TARGET = new ProxyLogTarget(SYSTEM_OUT_TARGET);
    private static final ThreadLocalLogTarget THREAD_TARGET = new ThreadLocalLogTarget(SYSTEM_OUT_TARGET);

    public static void initialize() {

        // try {
        setLevel(getLevelFromName(Environment.getSettings().getLogLevel()));

        // } catch (Exception e) {}
    }


    public static Logger getLogger(Class klass) {

        String className = klass.getName();
        String shortName = className.substring(className.lastIndexOf('.') + 1);

        return getLogger(shortName);
    }


    public static Logger getLogger(String topic) {
        return new Logger(topic, PROXY_LOG_TARGET);
    }


    public static int getLevelFromName(String levelName) {

        Assert.assertNotNull("levelName", levelName);
        Assert.assertNotNull("LEVEL_NAMES", LEVEL_NAMES);

        levelName = levelName.toUpperCase();

        for (int i = 0; i < LEVEL_NAMES.length; i++) {
            if (levelName.equals(LEVEL_NAMES[i])) {
                return i;
            }
        }

        throw new JRatException("log level '" + levelName + "' is not known");
    }


    public static void setLevel(int level) {
        PROXY_LOG_TARGET.setCurrentLevel(level);
    }


    public static int getLevel() {
        return PROXY_LOG_TARGET.getCurrentLevel();
    }


    public static void disableLogging() {
        setLogTarget(NULL_LOG_TARGET);
    }


    public static void enableThreadBasedLogging() {
        setLogTarget(THREAD_TARGET);
    }


    public static void enableSystemOutLogging() {
        setLogTarget(SYSTEM_OUT_TARGET);
    }

    public static synchronized void setLogTarget(LogTarget logTarget) {
        PROXY_LOG_TARGET.setLogTarget(logTarget);
    }

    public static synchronized LogTarget getLogTarget() {
        return PROXY_LOG_TARGET.getLogTarget();
    }

    public static void redirectLogging(PrintWriter printWriter) {

        TandemTarget tandemTarget = new TandemTarget(SYSTEM_OUT_TARGET, new WriterLogTarget(printWriter));

        setLogTarget(tandemTarget);
    }


    /**
     * this will only have any effect on logging if the current mode is using
     * the ThreadLocalLogTarget - meaning a call to enableDesktopLoggingMode was
     * made.
     */
    public static void executeInThreadScope(LogTarget newTarget, Runnable runnable) {
        THREAD_TARGET.executeInScope(newTarget, runnable);
    }
}
