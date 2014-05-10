package org.shiftone.jrat.util.log;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class LoggingManager implements Constants, LoggingManagerMBean {

    @Override
    public void makeLevelLoud() {
        LoggerFactory.setLevel(LEVEL_TRACE);
    }

    @Override
    public void setLevel(String levelName) {
        LoggerFactory.setLevel(LoggerFactory.getLevelFromName(levelName));
    }

    @Override
    public String getLevel() {
        return LEVEL_NAMES[LoggerFactory.getLevel()];
    }

    @Override
    public void disableLogging() {
        LoggerFactory.disableLogging();
    }

    @Override
    public void enableSystemOutLogging() {
        LoggerFactory.enableSystemOutLogging();
    }
}
