package org.shiftone.jrat.util.io.csv.field;



/**
 * @author Jeff Drost
 *
 */
public class LongField implements Field {

    public static Field INSTANCE = new LongField();

    public String format(Object value) {

        return (value == null)
               ? null
               : String.valueOf(value);
    }


    public Object parse(String value) {

        return (value == null)
               ? null
               : Long.valueOf(value);
    }
}
