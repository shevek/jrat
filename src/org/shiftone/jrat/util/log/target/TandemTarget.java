package org.shiftone.jrat.util.log.target;


import org.shiftone.jrat.util.log.Logger;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TandemTarget implements LogTarget {

    private static final Logger LOG = Logger.getLogger(TandemTarget.class);
    private LogTarget a;
    private LogTarget b;

    public TandemTarget(LogTarget a, LogTarget b) {
        this.a = a;
        this.b = b;
    }


    public void log(String topic, int level, Object message, Throwable throwable) {

        try {
            a.log(topic, level, message, throwable);
        }
        catch (Throwable e) {
        }

        try {
            b.log(topic, level, message, throwable);
        }
        catch (Throwable e) {
        }
    }


    public boolean isLevelEnabled(String topic, int level) {
        return a.isLevelEnabled(topic, level);
    }
}
