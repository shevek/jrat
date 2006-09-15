package org.shiftone.jrat.util.log.target;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
public interface LogTarget {

    void log(String topic, int level, Object message, Throwable throwable);


    boolean isLevelEnabled(String topic, int level);
}
