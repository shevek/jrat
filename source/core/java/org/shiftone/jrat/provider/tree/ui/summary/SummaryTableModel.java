package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.desktop.util.Table;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryTableModel extends AbstractTableModel {

    private static final Table TABLE = new Table();
    public static final Table.Column PACKAGE = TABLE.column("Package", false);
    public static final Table.Column CLASS = TABLE.column("Class");
    public static final Table.Column METHOD = TABLE.column("Method");
    public static final Table.Column SIGNATURE = TABLE.column("Signature", false);
    public static final Table.Column ENTERS = TABLE.column("Enters", false);
    public static final Table.Column EXITS = TABLE.column("Exits");
    public static final Table.Column EXCEPTIONS = TABLE.column("Exceptions Thrown", false);
    public static final Table.Column EXCEPTION_RATE = TABLE.column("Exception Rate", false);
    public static final Table.Column UNCOMPLETED = TABLE.column("Uncompleted Calls", false);
    public static final Table.Column TOTAL = TABLE.column("Total ms");
    public static final Table.Column MIN = TABLE.column("Min ms", false);
    public static final Table.Column MAX = TABLE.column("Max ms", false);
    public static final Table.Column AVERAGE = TABLE.column("Average ms");
    public static final Table.Column TOTAL_METHOD = TABLE.column("Total Method ms");
    public static final Table.Column AVERAGE_METHOD = TABLE.column("Average Method ms");
    public static final Table.Column TOTAL_CALLERS = TABLE.column("Total Callers", false);


    private final List methodSummaryList;

    public SummaryTableModel(MethodSummaryModel methodSummaryModel) {
        this.methodSummaryList = methodSummaryModel.getMethodSummaryList();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        MethodSummary method = (MethodSummary) methodSummaryList.get(rowIndex);

        if (columnIndex == PACKAGE.getIndex()) {
            return method.getMethodKey().getPackageName();
        }
        if (columnIndex == CLASS.getIndex()) {
            return method.getMethodKey().getClassName();
        }
        if (columnIndex == METHOD.getIndex()) {
            return method.getMethodKey().getShortMethodDescription();
        }
        if (columnIndex == SIGNATURE.getIndex()) {
            return method.getMethodKey().getSig().getShortText();
        }
        if (columnIndex == ENTERS.getIndex()) {
            return new Long(method.getTotalEnters());
        }
        if (columnIndex == EXITS.getIndex()) {
            return new Long(method.getTotalExists());
        }
        if (columnIndex == EXCEPTIONS.getIndex()) {
            return new Long(method.getTotalErrors());
        }
        if (columnIndex == EXCEPTION_RATE.getIndex()) {
            return method.getErrorRate();
        }
        if (columnIndex == UNCOMPLETED.getIndex()) {
            return new Long(method.getUncompletedCalls());
        }
        if (columnIndex == TOTAL.getIndex()) {
            return new Long(method.getTotalDuration());
        }
        if (columnIndex == MIN.getIndex()) {
            return method.getMinDuration();
        }
        if (columnIndex == MAX.getIndex()) {
            return method.getMaxDuration();
        }
        if (columnIndex == AVERAGE.getIndex()) {
            return method.getAverageDuration();
        }
        if (columnIndex == TOTAL_METHOD.getIndex()) {
            return method.getTotalMethodDuration();
        }
        if (columnIndex == AVERAGE_METHOD.getIndex()) {
            return method.getAverageMethodDuration();
        }
        if (columnIndex == TOTAL_CALLERS.getIndex()) {
            return new Integer(method.getTotalCallers());

        }
        throw new IllegalArgumentException("columnIndex = " + columnIndex);
    }

    public static List getColumns() {
        return TABLE.getColumns();
    }

    public int getRowCount() {
        return methodSummaryList.size();
    }

    public int getColumnCount() {
        return TABLE.getColumnCount();
    }

    public String getColumnName(int column) {
        return TABLE.getColumn(column).getName();
    }


}
