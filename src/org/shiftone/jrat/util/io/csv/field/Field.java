package org.shiftone.jrat.util.io.csv.field;



/**
 * @author Jeff Drost
 * @version $Revision: 1.3 $
 */
public interface Field {

    String format(Object value);


    Object parse(String value);
}
