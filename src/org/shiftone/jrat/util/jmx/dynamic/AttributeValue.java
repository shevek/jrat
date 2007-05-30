package org.shiftone.jrat.util.jmx.dynamic;



/**
 * @author Jeff Drost
 *
 */
public interface AttributeValue {

    String getType();


    String getDescription();


    void setValue(Object value);


    Object getValue();


    boolean isReadable();


    boolean isWritable();
}
