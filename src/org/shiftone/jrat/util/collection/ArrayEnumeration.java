package org.shiftone.jrat.util.collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Enumeration;

/**
 * @author Jeff Drost
 */
public class ArrayEnumeration implements Enumeration {

    private Object[] array;
    private int index = 0;
 
    public ArrayEnumeration(Object[] array) {
        this.array = array;
    }

    public boolean hasMoreElements() {
        return index < array.length;
    }

    public Object nextElement() {
        return array[index++];
    }
}
