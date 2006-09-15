package org.shiftone.jrat.util.log.target;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
public class NullLogTarget implements LogTarget {

    public static final NullLogTarget INSTANCE = new NullLogTarget();

    public void log(String topic, int level, Object message, Throwable throwable) {}


    public boolean isLevelEnabled(String topic, int level) {
        return false;
    }
}
