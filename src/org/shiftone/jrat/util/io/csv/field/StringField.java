package org.shiftone.jrat.util.io.csv.field;


/**
 * @author Jeff Drost
 */
public class StringField implements Field {

    public static Field INSTANCE = new StringField();

    public String format(Object value) {

        return (value == null)
                ? null
                : String.valueOf(value);
    }


    public Object parse(String value) {
        return value;
    }
}
