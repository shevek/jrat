package org.shiftone.jrat.util.collection;

import java.util.Enumeration;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ArrayEnumeration implements Enumeration {

    private final Object[] array;
    private int index = 0;

    public ArrayEnumeration(Object[] array) {
        this.array = array;
    }

    @Override
    public boolean hasMoreElements() {
        return index < array.length;
    }

    @Override
    public Object nextElement() {
        return array[index++];
    }
}
