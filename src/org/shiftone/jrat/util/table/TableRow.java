package org.shiftone.jrat.util.table;



/**
 * Interface TableRow
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.8 $
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
