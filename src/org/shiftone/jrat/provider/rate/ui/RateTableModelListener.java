package org.shiftone.jrat.provider.rate.ui;



import org.shiftone.jrat.ui.util.graph.GraphModelSet;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * A bridge between a RateModelTableModel and a GraphModelSet.
 *
 * This class receieves events from a RateTableModel and hides and unhides the
 * necessary graph within a GraphModelSet.
 *
 * @author Jeff Drost
 *
 */
public class RateTableModelListener implements TableModelListener {

    private static final Logger LOG           = Logger.getLogger(RateTableModelListener.class);
    private RateModelTableModel tableModel    = null;
    private GraphModelSet       graphModelSet = null;

    /**
     * Constructor RateTableModelListener
     *
     *
     * @param tableModel
     * @param graphModelSet
     */
    public RateTableModelListener(RateModelTableModel tableModel, GraphModelSet graphModelSet) {

        this.graphModelSet = graphModelSet;
        this.tableModel    = tableModel;

        syncWithTable(0, tableModel.getRowCount() - 1);
    }


    /**
     * Method syncWithTable
     */
    private void syncWithTable(int firstRow, int lastRow) {

        Boolean value = null;

        for (int row = firstRow; row <= lastRow; row++)
        {
            value = (Boolean) tableModel.getValueAt(row, RateModelTableModel.FLAG_COLUMN_INDEX);

            if (value.booleanValue() == true)
            {
                graphModelSet.unhide(new Integer(row));
            }
            else
            {
                graphModelSet.hide(new Integer(row));
            }
        }
    }


    /**
     * Method tableChanged
     */
    public void tableChanged(TableModelEvent e) {

        if (e.getType() == TableModelEvent.UPDATE)
        {
            syncWithTable(e.getFirstRow(), e.getLastRow());
        }
    }
}
