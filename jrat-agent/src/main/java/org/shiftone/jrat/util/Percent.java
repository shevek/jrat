package org.shiftone.jrat.util;

import java.text.DecimalFormat;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Percent extends Number implements Comparable<Percent> {

    private static final Logger LOG = Logger.getLogger(Percent.class);
    public static final Percent ZERO = new Percent(0);
    public static final Percent HUNDRED = new Percent(100);
    private static final DecimalFormat pctDecimalFormat = new DecimalFormat("#,###.0");
    private final double value;

    // Might be NaN.
    public Percent(double value) {
        this.value = value;
    }

    public boolean isNaN() {
        return Double.isNaN(value);
    }

    public boolean isInfinite() {
        return Double.isInfinite(value);
    }

    @Override
    public String toString() {
        synchronized (pctDecimalFormat) {
            return pctDecimalFormat.format(value);
        }
    }

    @Override
    public byte byteValue() {
        return (byte) value;
    }

    @Override
    public short shortValue() {
        return (short) value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public int hashCode() {

        long bits = Double.doubleToLongBits(value);

        return (int) (bits ^ (bits >>> 32));
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Number) && ((((Number) obj).doubleValue()) == value));
    }

    @Override
    public int compareTo(Percent anotherPercent) {
        Assert.assertNotNull("anotherPercent", anotherPercent);
        return Double.compare(value, anotherPercent.value);
    }
}
