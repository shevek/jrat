package org.shiftone.jrat.ui.viewer.tsv;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Class TsvMouseAdapter
 *
 * @author Jeff Drost
 */
public class TsvMouseAdapter extends MouseAdapter {

    private static final Logger LOG = Logger.getLogger(TsvMouseAdapter.class);
    private JTable table = null;
    private TsvTableModel model = null;
    private boolean[] sortAscending = null;

    /**
     * Method TsvMouseAdapter
     *
     * @param table
     */
    public TsvMouseAdapter(JTable table) {

        this.table = table;
        this.model = (TsvTableModel) table.getModel();
        sortAscending = new boolean[this.model.getColumnCount()];
    }


    /**
     * Method mouseClicked
     */
    public void mouseClicked(MouseEvent e) {

        TableColumnModel columnModel = table.getColumnModel();
        int viewColumn = columnModel.getColumnIndexAtX(e.getX());
        int column = table.convertColumnIndexToModel(viewColumn);

        model.sortByColumn(column, sortAscending[column]);

        sortAscending[column] = !sortAscending[column];
    }
}
