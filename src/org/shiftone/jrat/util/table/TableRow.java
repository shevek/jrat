package org.shiftone.jrat.util.table;


/**
 * Interface TableRow
 *
 * @author Jeff Drost
 */
public interface TableRow {

    /**
     * Method getValueCount
     */
    int getValueCount();


    /**
     * Method getValueAt
     */
    Object getValueAt(int index);
}
