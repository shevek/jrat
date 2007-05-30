package org.shiftone.jrat.util.log;


/**
 * @author Jeff Drost
 */
public interface LoggingManagerMBean {

    void makeLevelLoud();


    void setLevel(String levelName);


    String getLevel();


    void disableLogging();


    void enableSystemOutLogging();
}
