package org.shiftone.jrat.util.io.csv.field;


import org.shiftone.jrat.core.JRatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DateField implements Field {

    public static Field INSTANCE_ISO8601 = new DateField();
    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private final DateFormat dateFormat;

    public DateField() {
        this.dateFormat = new SimpleDateFormat(ISO8601);
    }


    public DateField(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


    public DateField(String dateFormatText) {
        this.dateFormat = new SimpleDateFormat(dateFormatText);
    }


    public String format(Object value) {

        return (value == null)
                ? null
                : dateFormat.format(value);
    }


    public Object parse(String value) {

        try {
            return (value == null)
                    ? null
                    : dateFormat.parse(value);
        }
        catch (ParseException e) {
            throw new JRatException("failed to parse date : " + value, e);
        }
    }
}
