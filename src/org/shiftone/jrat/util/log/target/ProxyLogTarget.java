package org.shiftone.jrat.util.log.target;



import org.shiftone.jrat.util.log.Constants;


/**
 * This class is an indirection layer to the "real" LogTarget. This makes it
 * possible to switch the LogTarget that is being used by many LogTargetLog
 * instances in one statement.
 *
 * @author Jeff Drost
 *
 */
public class ProxyLogTarget implements LogTarget, Constants {

    private LogTarget logTarget;
    private int       currentLevel = DEFAULT_LEVEL;

    public ProxyLogTarget(LogTarget logTarget) {
        this.logTarget = logTarget;
    }


    public boolean isLevelEnabled(String topic, int level) {
        return (level >= currentLevel);
    }


    public LogTarget getLogTarget() {
        return logTarget;
    }


    public void setLogTarget(LogTarget logTarget) {
        this.logTarget = logTarget;
    }


    public int getCurrentLevel() {
        return currentLevel;
    }


    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }


    public void log(String topic, int level, Object message, Throwable throwable) {

        if (isLevelEnabled(topic, level))
        {
            logTarget.log(topic, level, message, throwable);
        }
    }
}
