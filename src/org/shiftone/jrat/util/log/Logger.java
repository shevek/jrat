package org.shiftone.jrat.util.log;



import org.shiftone.jrat.util.log.target.LogTarget;


/**
 * Interface Log
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.13 $
 */
public class Logger implements LogTarget, Constants {

    private final LogTarget target;
    private final String    topic;

    Logger(String topic, LogTarget target) {
        this.topic  = topic;
        this.target = target;
    }


    public static Logger getLogger(Class klass) {
        return LoggerFactory.getLogger(klass);
    }


    public static Logger getLogger(String topic) {
        return LoggerFactory.getLogger(topic);
    }


    public void log(String topic, int level, Object message, Throwable throwable) {
        target.log(topic, level, message, throwable);
    }


    public boolean isLevelEnabled(String topic, int level) {
        return (target != null) && target.isLevelEnabled(topic, level);
    }


    public boolean isLevelEnabled(int level) {
        return isLevelEnabled(topic, level);
    }


    public boolean isInfoEnabled() {
        return isLevelEnabled(Logger.LEVEL_INFO);
    }


    public boolean isDebugEnabled() {
        return isLevelEnabled(Logger.LEVEL_DEBUG);
    }


    public boolean isTraceEnabled() {
        return isLevelEnabled(Logger.LEVEL_TRACE);
    }


    public void trace(Object obj) {
        log(topic, Logger.LEVEL_TRACE, obj, null);
    }


    public void debug(Object obj) {
        log(topic, Logger.LEVEL_DEBUG, obj, null);
    }


    public void debug(Object obj, Throwable t) {
        log(topic, Logger.LEVEL_DEBUG, obj, t);
    }


    public void info(Object obj) {
        log(topic, Logger.LEVEL_INFO, obj, null);
    }


    public void info(Object obj, Throwable t) {
        log(topic, Logger.LEVEL_INFO, obj, t);
    }


    public void warn(Object obj) {
        log(topic, Logger.LEVEL_WARN, obj, null);
    }


    public void warn(Object obj, Throwable t) {
        log(topic, Logger.LEVEL_WARN, obj, t);
    }


    public void error(Object obj) {
        log(topic, Logger.LEVEL_ERROR, obj, null);
    }


    public void error(Object obj, Throwable t) {
        log(topic, Logger.LEVEL_ERROR, obj, t);
        t.printStackTrace(System.err);
    }


    public void fatal(Object obj) {
        log(topic, Logger.LEVEL_FATAL, obj, null);
    }


    public void fatal(Object obj, Throwable t) {
        log(topic, Logger.LEVEL_FATAL, obj, t);
    }
}
