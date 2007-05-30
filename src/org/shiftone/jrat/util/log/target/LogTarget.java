package org.shiftone.jrat.util.log.target;


/**
 * @author Jeff Drost
 */
public interface LogTarget {

    void log(String topic, int level, Object message, Throwable throwable);


    boolean isLevelEnabled(String topic, int level);
}
