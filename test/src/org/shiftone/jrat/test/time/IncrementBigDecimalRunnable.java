package org.shiftone.jrat.test.time;

import java.math.BigDecimal;


/**
 * @author Jeff Drost
 */
public class IncrementBigDecimalRunnable implements Runnable {

    private BigDecimal bigDecimal = new BigDecimal("0");
    private static BigDecimal INCREMENT = new BigDecimal("1");

    public void run() {
        bigDecimal.add(INCREMENT);
    }

    public String toString() {
        return "add two BigDecimals";
    }
}