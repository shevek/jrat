package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.desktop.util.ColumnInfo;

import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryTableModel extends AbstractTableModel {

    public static final int TOTAL_METHOD_MS_INDEX = 12;
    public static final int ERROR_RATE_INDEX = 6;

    public static final ColumnInfo[] COLUMNS = {
            new ColumnInfo("Package", false),// 0
            new ColumnInfo("Class"),// 1
            new ColumnInfo("Method Name"),// 2
            new ColumnInfo("Enters", false),// 3
            new ColumnInfo("Exits"),// 4
            new ColumnInfo("Exceptions Thrown", false),// 5
            new ColumnInfo("Exception Rate", false),// 6
            new ColumnInfo("Uncompleted Calls", false),// 7
            new ColumnInfo("Total ms", false),// 8
            new ColumnInfo("Min Duration ms", false),// 9
            new ColumnInfo("Max Duration ms", false),// 10
            new ColumnInfo("Average ms", false),// 11
            new ColumnInfo("Total Method ms"),// 12
            new ColumnInfo("Average Method ms"),// 13
            new ColumnInfo("Total Callers", false),// 14
    };

    private final List methodSummaryList;

    public SummaryTableModel(MethodSummaryModel methodSummaryModel) {
        this.methodSummaryList = methodSummaryModel.getMethodSummaryList();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        MethodSummary method = (MethodSummary)methodSummaryList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return method.getMethodKey().getPackageName();
            case 1:
                return method.getMethodKey().getClassName();
            case 2:
                return method.getMethodKey().getShortMethodDescription();
            case 3:
                return new Long(method.getTotalEnters());
            case 4:
                return new Long(method.getTotalExists());
            case 5:
                return new Long(method.getTotalErrors());
            case 6:
                return method.getErrorRate();
            case 7:
                return new Long(method.getUncompletedCalls());
            case 8:
                return new Long(method.getTotalDuration());
            case 9:
                return method.getMinDuration();
            case 10:
                return method.getMaxDuration();
            case 11:
                return method.getAverageDuration();
            case 12:
                return method.getTotalMethodDuration();
            case 13:
                return method.getAverageMethodDuration();
            case 14:
                return new Integer(method.getTotalCallers());
        }
        throw new IllegalArgumentException("columnIndex = " + columnIndex);
    }

    public static List getColumnInfos() {
        return Collections.unmodifiableList(Arrays.asList(COLUMNS));
    }

    public int getRowCount() {
        return methodSummaryList.size();
    }

    public int getColumnCount() {
        return COLUMNS.length;
    }

    public String getColumnName(int column) {
        return COLUMNS[column].getTitle();
    }

  

}
