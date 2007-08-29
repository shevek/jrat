package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.desktop.util.ColumnInfo;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.util.Percent;

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

    private List methodList = new ArrayList();

    public SummaryTableModel(StackTreeNode node) {
        Map cache = new HashMap();
        process(node, cache);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Method method = getMethod(rowIndex);
        switch (columnIndex) {
            case 0:
                return method.methodKey.getPackageName();
            case 1:
                return method.methodKey.getClassName();
            case 2:
                return method.methodKey.getShortMethodDescription();
            case 3:
                return new Long(method.totalEnters);
            case 4:
                return new Long(method.totalExists);
            case 5:
                return new Long(method.totalErrors);
            case 6:
                return method.getErrorRate();
            case 7:
                return new Long(method.totalEnters - method.totalExists);
            case 8:
                return new Long(method.totalDuration);
            case 9:
                return method.minDuration == Long.MAX_VALUE ? null : new Long(method.minDuration);
            case 10:
                return method.maxDuration == Long.MIN_VALUE ? null : new Long(method.maxDuration);
            case 11:
                return method.getAverageDuration();
            case 12:
                return method.getTotalMethodDuration();
            case 13:
                return method.getAverageMethodDuration();
            case 14:
                return new Integer(method.totalCallers);
        }
        throw new IllegalArgumentException("columnIndex = " + columnIndex);
    }

    public static List getColumnInfos() {
        return Collections.unmodifiableList(Arrays.asList(COLUMNS));
    }

    public int getRowCount() {
        return methodList.size();
    }

    public int getColumnCount() {
        return COLUMNS.length;
    }

    public String getColumnName(int column) {
        return COLUMNS[column].getTitle();
    }


    private Method getMethod(int rowIndex) {
        return (Method) methodList.get(rowIndex);
    }

    public List getMethods() {
        return methodList;
    }

    private void process(StackTreeNode node, Map cache) {

        if (!node.isRootNode()) {
            MethodKey methodKey = node.getMethodKey();
            Method method = getMethod(methodKey, cache);
            addStatistics(node, method);
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            StackTreeNode child = node.getChildNodeAt(i);
            process(child, cache);
        }
    }

    private void addStatistics(StackTreeNode node, Method method) {
        Accumulator accumulator = node.getAccumulator();
        method.totalEnters += accumulator.getTotalEnters();
        method.totalExists += accumulator.getTotalExits();
        method.totalErrors += accumulator.getTotalErrors();
        if (method.totalExists > 0) {
            // if the node has not been existed, then the min and max times
            // will only have the MAX_VALUE and MIN_VALUE.
            method.minDuration = Math.min(method.minDuration, accumulator.getMinDuration());
            method.maxDuration = Math.max(method.maxDuration, accumulator.getMaxDuration());
        }
        method.totalDuration += accumulator.getTotalDuration();
        method.totalMethodDuration += node.getTotalMethodDuration();
        method.totalCallers++;
    }

    private Method getMethod(MethodKey methodKey, Map cache) {
        Method method = (Method) cache.get(methodKey);
        if (method == null) {
            method = new Method(methodKey);
            cache.put(methodKey, method);
            methodList.add(method);
        }
        return method;
    }


    private static class Method {

        private final MethodKey methodKey;
        private long totalEnters;
        private long totalExists;
        private long totalErrors;
        private long minDuration = Long.MAX_VALUE;
        private long maxDuration = Long.MIN_VALUE;
        private long totalDuration;
        private long totalMethodDuration;
        private int totalCallers;


        public Method(MethodKey methodKey) {
            this.methodKey = methodKey;
        }

        /**
         * It the method has been entered but not exited, then it is
         * possible that the method time would end up negative.  I'm not
         * showing it at all in this case to avoid confusion.
         */
        public Long getTotalMethodDuration() {
            return totalEnters != totalExists
                    ? null
                    : new Long(totalMethodDuration);
        }

        public Double getAverageMethodDuration() {
            return (totalExists == 0) || (totalEnters != totalExists)
                    ? null
                    : new Double((double) totalMethodDuration / (double) totalExists);
        }

        public Double getAverageDuration() {
            return totalExists == 0
                    ? null
                    : new Double((double) totalDuration / (double) totalExists);
        }

        public Percent getErrorRate() {
            return (totalExists == 0)
                    ? null
                    : new Percent(((double) totalErrors * 100.0) / (double) totalExists);
        }
    }

}
