package org.shiftone.jrat.util.io.csv.field;



/**
 * @author Jeff Drost
 *
 */
public interface Field {

    String format(Object value);


    Object parse(String value);
}
