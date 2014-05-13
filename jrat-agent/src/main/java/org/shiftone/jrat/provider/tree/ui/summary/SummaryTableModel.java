package org.shiftone.jrat.provider.tree.ui.summary;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.shiftone.jrat.desktop.util.Table;
import org.shiftone.jrat.util.Percent;

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
    public static final Table.Column TOTAL_METHOD = TABLE.column("Total Self ms");
    public static final Table.Column PERCENT_METHOD = TABLE.column("Self Time %");
    public static final Table.Column AVERAGE_METHOD = TABLE.column("Average Self ms");
    public static final Table.Column TOTAL_CALLERS = TABLE.column("Total Callers", false);

    public static List<? extends Table.Column> getColumns() {
        return TABLE.getColumns();
    }

    private final MethodSummaryModel summaryModel;
    private final List<? extends MethodSummary> methodSummaryList;

    public SummaryTableModel(MethodSummaryModel summaryModel) {
        this.summaryModel = summaryModel;
        this.methodSummaryList = summaryModel.getMethodSummaryList();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MethodSummary summary = (MethodSummary) methodSummaryList.get(rowIndex);

        if (columnIndex == PACKAGE.getIndex()) {
            return summary.getMethodKey().getPackageName();
        }
        if (columnIndex == CLASS.getIndex()) {
            return summary.getMethodKey().getClassName();
        }
        if (columnIndex == METHOD.getIndex()) {
            return summary.getMethodKey().getShortMethodDescription();
        }
        if (columnIndex == SIGNATURE.getIndex()) {
            return summary.getMethodKey().getSig().getShortText();
        }
        if (columnIndex == ENTERS.getIndex()) {
            return summary.getTotalEnters();
        }
        if (columnIndex == EXITS.getIndex()) {
            return summary.getTotalExits();
        }
        if (columnIndex == EXCEPTIONS.getIndex()) {
            return summary.getTotalErrors();
        }
        if (columnIndex == EXCEPTION_RATE.getIndex()) {
            return summary.getErrorRate();
        }
        if (columnIndex == UNCOMPLETED.getIndex()) {
            return summary.getUncompletedCalls();
        }
        if (columnIndex == TOTAL.getIndex()) {
            return summary.getTotalDuration();
        }
        if (columnIndex == MIN.getIndex()) {
            return summary.getMinDuration();
        }
        if (columnIndex == MAX.getIndex()) {
            return summary.getMaxDuration();
        }
        if (columnIndex == AVERAGE.getIndex()) {
            return summary.getMeanDuration();
        }
        if (columnIndex == TOTAL_METHOD.getIndex()) {
            return summary.getTotalSelfDuration();
        }
        if (columnIndex == AVERAGE_METHOD.getIndex()) {
            return summary.getMeanSelfDuration();
        }
        if (columnIndex == TOTAL_CALLERS.getIndex()) {
            return summary.getTotalCallers();
        }

        if (columnIndex == PERCENT_METHOD.getIndex()) {
            return getPercent(summary);
        }

        throw new IllegalArgumentException("columnIndex = " + columnIndex);
    }

    private Percent getPercent(MethodSummary summary) {
        Number tmd = summary.getTotalSelfDuration();
        if (tmd == null)
            return null;
        return new Percent(tmd.doubleValue() * 100.0 / (double) summaryModel.getTotalSelfDuration());
    }

    @Override
    public int getRowCount() {
        return methodSummaryList.size();
    }

    @Override
    public int getColumnCount() {
        return TABLE.getColumnCount();
    }

    @Override
    public String getColumnName(int column) {
        return TABLE.getColumn(column).getName();
    }

}
