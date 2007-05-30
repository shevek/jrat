package org.shiftone.jrat.provider.rate.ui;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;


/**
 * Table model that provides a view of the RateModel containing each method.
 * Allows selection and deselection of methods, which add and remove graphs from
 * the viewer.
 *
 * @author Jeff Drost
 */
public class RateModelTableModel extends AbstractTableModel {

    private static final Logger LOG = Logger.getLogger(RateModelTableModel.class);
    public static final int FLAG_COLUMN_INDEX = 1;
    private static final int CHECKED_AT_START = 4;
    private static final String[] COLUMN_NAMES = {"Method", "Visible", "Color"};
    private static final Class[] COLUMN_TYPES = {String.class, Boolean.class, Color.class};
    private RateModel rateModel = null;
    private Boolean[] showFlags = null;

    /**
     * Constructor RateModelTableModel
     *
     * @param rateModel
     */
    public RateModelTableModel(RateModel rateModel) {

        this.rateModel = rateModel;
        this.showFlags = new Boolean[rateModel.getMethodCount()];

        for (int i = 0; i < showFlags.length; i++) {
            showFlags[i] = (i < CHECKED_AT_START)
                    ? Boolean.TRUE
                    : Boolean.FALSE;
        }
    }


    /**
     * Method isCellEditable
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        // method name or visible flag
        return (columnIndex <= 1);
    }


    /**
     * Method getColumnClass
     */
    public Class getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }


    /**
     * Method getColumnName
     */
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }


    /**
     * Method getColumnCount
     */
    public int getColumnCount() {
        return 3;
    }


    /**
     * Method getRowCount
     */
    public int getRowCount() {
        return rateModel.getMethodCount();
    }


    /**
     * Method setValueAt
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if (columnIndex == 1) {
            showFlags[rowIndex] = (Boolean) aValue;

            super.fireTableCellUpdated(rowIndex, columnIndex);
        }
    }


    /**
     * Method getValueAt
     */
    public Object getValueAt(int rowIndex, int columnIndex) {

        MethodKey methodKey = rateModel.getMethodKey(rowIndex);
        Color color = rateModel.getMethodColor(rowIndex);
        Object obj = null;

        switch (columnIndex) {

            case 0:
                obj = methodKey;
                break;

            case 1:
                obj = showFlags[rowIndex];
                break;

            case 2:
                obj = color;
                break;
        }

        return obj;
    }
}
