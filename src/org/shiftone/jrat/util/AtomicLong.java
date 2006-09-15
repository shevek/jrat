package org.shiftone.jrat.util;



import org.shiftone.jrat.util.log.Logger;


/**
 * Before there was java.util.concurrent.atomic.AtomicLong, there was
 * org.shiftone.jrat.util.Sequence. This class was since renamed to make it
 * obvious what it does. This class does synchronize, while AtomicLong uses a
 * magic native method. This class supports Java 1.4.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.9 $
 */
public class AtomicLong {

    private static final Logger LOG = Logger.getLogger(AtomicLong.class);
    private long                value;

    public AtomicLong() {
        value = 0;
    }


    public AtomicLong(long initialValue) {
        value = initialValue;
    }


    public synchronized long get() {
        return value;
    }


    public long incrementAndGet() {
        return addAndGet(1);
    }


    public synchronized long addAndGet(long delta) {

        value += delta;

        return value;
    }


    public String toString() {
        return String.valueOf(value);
    }
}
