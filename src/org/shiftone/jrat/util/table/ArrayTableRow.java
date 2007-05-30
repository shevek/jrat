package org.shiftone.jrat.util.table;



import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class ArrayTableRow implements TableRow {

    private static final Logger LOG   = Logger.getLogger(ArrayTableRow.class);
    Object[]                    array = null;

    public ArrayTableRow(Object[] array) {
        this.array = array;
    }


    public Object getValueAt(int index) {
        return array[index];
    }


    public int getValueCount() {
        return array.length;
    }
}
