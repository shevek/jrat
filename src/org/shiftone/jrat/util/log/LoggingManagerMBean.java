package org.shiftone.jrat.util.log;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
public interface LoggingManagerMBean {

    void makeLevelLoud();


    void setLevel(String levelName);


    String getLevel();


    void disableLogging();


    void enableSystemOutLogging();
}
