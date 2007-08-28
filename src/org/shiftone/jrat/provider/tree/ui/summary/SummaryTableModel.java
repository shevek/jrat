package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;

import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryTableModel extends AbstractTableModel {

    private static final String[] COLUMNS = {"Package", "Class", "Method", "Enters", "Exits", "Duration"};
    private List methodList = new ArrayList();

    public SummaryTableModel(StackTreeNode node) {
        Map cache = new HashMap();
        process(node, cache);
    }

    public int getRowCount() {
        return methodList.size();
    }

    public int getColumnCount() {
        return COLUMNS.length;
    }

    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Method method = getMethod(rowIndex);
        switch (columnIndex) {
            case 0 :
                return method.methodKey.getPackageName();
            case 1 :
                return method.methodKey.getClassName();
            case 2 :
                return method.methodKey.getShortMethodDescription();
            case 3 :
                return new Long(method.totalEnters);
            case 4 :
                return new Long(method.totalExists);
            case 5 :
                return new Long(method.totalDuration);
        }
        throw new IllegalArgumentException("columnIndex = " + columnIndex);
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

        for (int i = 0 ; i < node.getChildCount() ; i ++) {
            StackTreeNode child = node.getChildNodeAt(i);
            process(child, cache);
        }
    }

    private void addStatistics(StackTreeNode node, Method method) {
        Accumulator accumulator = node.getAccumulator();
        method.totalEnters += accumulator.getTotalEnters();
        method.totalExists += accumulator.getTotalExits();
        method.totalDuration += accumulator.getTotalDuration();
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

        public Method(MethodKey methodKey) {
            this.methodKey = methodKey;
        }


    }

}
