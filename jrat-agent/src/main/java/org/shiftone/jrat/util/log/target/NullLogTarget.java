package org.shiftone.jrat.util.log.target;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class NullLogTarget implements LogTarget {

    public static final NullLogTarget INSTANCE = new NullLogTarget();

    @Override
    public void log(String topic, int level, Object message, Throwable throwable) {
    }

    @Override
    public boolean isLevelEnabled(String topic, int level) {
        return false;
    }
}
