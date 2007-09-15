package org.shiftone.jrat.test.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Jeff Drost
 */
public class SimpleDateFormatFormatRunnable implements Runnable {

    private Date date = new Date();
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public void run() {
        format.format(date);
    }

    public String toString() {
        return "simpleDateFormat.format(date)";
    }
}
