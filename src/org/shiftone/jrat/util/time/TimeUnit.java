package org.shiftone.jrat.util.time;


import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public class TimeUnit {

    private static final Logger LOG = Logger.getLogger(TimeUnit.class);
    public static final TimeUnit NANOSECONDS = new TimeUnit("nanosecond", 1);
    public static final TimeUnit MICROSECONDS = new TimeUnit("microsecond", NANOSECONDS.nanos * 1000);
    public static final TimeUnit MILLISECONDS = new TimeUnit("millisecond", MICROSECONDS.nanos * 1000);
    public static final TimeUnit SECONDS = new TimeUnit("second", MILLISECONDS.nanos * 1000);
    public static final TimeUnit MINUTES = new TimeUnit("minute", SECONDS.nanos * 60);
    public static final TimeUnit HOURS = new TimeUnit("hour", MINUTES.nanos * 60);
    public static final TimeUnit NANOS = NANOSECONDS;
    public static final TimeUnit MS = MILLISECONDS;
    private long nanos;
    private String name;

    private TimeUnit(String name, long nanos) {
        this.name = name;
        this.nanos = nanos;
    }


    public long fromNanos(long durationNanos) {
        return durationNanos / nanos;
    }


    public Long fromNanos(Long durationNanos) {

        return (durationNanos == null)
                ? null
                : new Long(durationNanos.longValue() / nanos);
    }


    public Float fromNanos(Float durationNanos) {

        return (durationNanos == null)
                ? null
                : new Float(durationNanos.floatValue() / nanos);
    }


    public Double fromNanos(Double durationNanos) {

        return (durationNanos == null)
                ? null
                : new Double(durationNanos.doubleValue() / nanos);
    }


    public long toNanos(long duration) {
        return duration * nanos;
    }
}
