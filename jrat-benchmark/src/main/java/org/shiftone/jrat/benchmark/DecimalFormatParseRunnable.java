package org.shiftone.jrat.benchmark;


import java.text.DecimalFormat;

/**
 * @author Jeff Drost
 */
public class DecimalFormatParseRunnable implements Runnable {
    private DecimalFormat decimalFormat = new DecimalFormat();
    private String source = "123456789.987654321";

    public void run() {
        try {
            decimalFormat.parse(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "decimalFormat.parse(decimal)";
    }
}
