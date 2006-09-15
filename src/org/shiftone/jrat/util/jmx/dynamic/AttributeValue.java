package org.shiftone.jrat.util.jmx.dynamic;



/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public interface AttributeValue {

    String getType();


    String getDescription();


    void setValue(Object value);


    Object getValue();


    boolean isReadable();


    boolean isWritable();
}
