package org.shiftone.jrat.util.io.csv.field;


/**
 * @author Jeff Drost
 */
public class DoubleField implements Field {

    public static Field INSTANCE = new DoubleField();

    public String format(Object value) {

        return (value == null)
                ? null
                : String.valueOf(value);
    }


    public Object parse(String value) {

        return (value == null)
                ? null
                : Double.valueOf(value);
    }
}
