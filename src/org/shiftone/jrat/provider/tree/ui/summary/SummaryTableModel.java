package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.desktop.util.ColumnInfo;

import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryTableModel extends AbstractTableModel {

    public static final int TOTAL_METHOD_MS_INDEX = 8;

    public static final ColumnInfo[] COLUMNS = {
            new ColumnInfo("Package", false),// 0
            new ColumnInfo("Class"),// 1
            new ColumnInfo("Method"),// 2
            new ColumnInfo("Enters", false),// 3
            new ColumnInfo("Exits"),// 4
            new ColumnInfo("Uncompleted Calls", false),// 5
            new ColumnInfo("Total ms", false),// 6
            new ColumnInfo("Average ms", false),// 7
            new ColumnInfo("Total Method ms"),// 8
            new ColumnInfo("Average Method ms"),// 9
            new ColumnInfo("Total Callers", false),// 10
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
                return new Long(method.totalEnters - method.totalExists);
            case 6:
                return new Long(method.totalDuration);
            case 7:
                return method.getAverageDuration();
            case 8:
                return new Long(method.totalMethodDuration);
            case 9:
                return method.getAverageMethodDuration();
            case 10:
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
        private long totalDuration;
        private long totalMethodDuration;
        private int totalCallers;


        public Method(MethodKey methodKey) {
            this.methodKey = methodKey;
        }

        public Double getAverageMethodDuration() {
            return totalExists == 0
                    ? null
                    : new Double((double) totalMethodDuration / (double) totalExists);
        }

        public Double getAverageDuration() {
            return totalExists == 0
                    ? null
                    : new Double((double) totalDuration / (double) totalExists);
        }


    }

}
