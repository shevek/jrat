package org.shiftone.jrat.util;


import org.shiftone.jrat.util.log.Logger;

import java.text.DecimalFormat;


/**
 * @author Jeff Drost
 */
public class Percent extends Number implements Comparable {

    private static final Logger LOG = Logger.getLogger(Percent.class);
    public static final Percent ZERO = new Percent(0);
    public static final Percent HUNDRED = new Percent(100);
    private static DecimalFormat pctDecimalFormat = new DecimalFormat("#,###.0");
    public static final double POSITIVE_INFINITY = 1.0 / 0.0;
    public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final double NAN = 0.0d / 0.0d;
    public static final double MAX_VALUE = 1.7976931348623157e+308;
    public static final double MIN_VALUE = 4.9e-324;
    private double value;

    public Percent(double value) {
        this.value = value;
    }


    public Percent(String s) throws NumberFormatException {

        // REMIND: this is inefficient
        this(valueOf(s).doubleValue());
    }


    public static String toString(double d) {
        return Double.toString(d);
    }


    public static Percent valueOf(String s) throws NumberFormatException {

        Assert.assertNotNull("string value", s);

        return new Percent(Double.parseDouble(s));
    }


    public static double parseDouble(String s) throws NumberFormatException {

        Assert.assertNotNull("string value", s);

        return Double.parseDouble(s);
    }


    public static boolean isNaN(double v) {
        return (v != v);
    }


    public static boolean isInfinite(double v) {
        return (v == POSITIVE_INFINITY) || (v == NEGATIVE_INFINITY);
    }


    public boolean isNaN() {
        return isNaN(value);
    }


    public boolean isInfinite() {
        return isInfinite(value);
    }


    public String toString() {

        synchronized (pctDecimalFormat) {
            return pctDecimalFormat.format(value);
        }

        // return String.valueOf(value);
    }


    public byte byteValue() {
        return (byte) value;
    }


    public short shortValue() {
        return (short) value;
    }


    public int intValue() {
        return (int) value;
    }


    public long longValue() {
        return (long) value;
    }


    public float floatValue() {
        return (float) value;
    }


    public double doubleValue() {
        return (double) value;
    }


    public int hashCode() {

        long bits = Double.doubleToLongBits(value);

        return (int) (bits ^ (bits >>> 32));
    }


    public boolean equals(Object obj) {
        return ((obj instanceof Number) && ((((Number) obj).doubleValue()) == value));
    }


    public int compareTo(Percent anotherPercent) {

        Assert.assertNotNull("anotherPercent", anotherPercent);

        Double me = new Double(value);
        Double other = new Double(anotherPercent.value);

        return me.compareTo(other);
    }


    public int compareTo(Object o) {
        return compareTo((Percent) o);
    }
}
