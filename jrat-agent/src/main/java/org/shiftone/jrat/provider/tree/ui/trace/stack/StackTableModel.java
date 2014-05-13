package org.shiftone.jrat.provider.tree.ui.trace.stack;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.desktop.util.Table;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.log.Logger;

/**
 * Class StackTableModel
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class StackTableModel extends AbstractTableModel {

    private static final Logger LOG = Logger.getLogger(StackTableModel.class);

    private static final Table TABLE = new Table(); // enum please?
    public static final Table.Column PACKAGE = TABLE.column("Package", false);
    public static final Table.Column CLASS = TABLE.column("Class");
    public static final Table.Column METHOD = TABLE.column("Method");
    public static final Table.Column SIGNATURE = TABLE.column("Signature");
    public static final Table.Column ENTERS = TABLE.column("Enters", false);
    public static final Table.Column EXITS = TABLE.column("Exits");
    public static final Table.Column ERRORS = TABLE.column("Errors", false);
    public static final Table.Column THREADS = TABLE.column("Concurrent Threads", false);
    public static final Table.Column TOTAL = TABLE.column("Total ms");
    public static final Table.Column AVERAGE = TABLE.column("Average ms", false);
    public static final Table.Column TOTAL_METHOD = TABLE.column("Total Self ms");
    public static final Table.Column AVERAGE_METHOD = TABLE.column("Average Self ms");
    public static final Table.Column STANDARD_DEVIATION = TABLE.column("Standard Deviation", false);
    public static final Table.Column MIN = TABLE.column("Min ms", false);
    public static final Table.Column MAX = TABLE.column("Max ms", false);
    public static final Table.Column PERCENT_OF_PARENT = TABLE.column("% of Parent");
    public static final Table.Column PERCENT_OF_ROOT = TABLE.column("% of Root");

    public static List<? extends Table.Column> getColumns() {
        return TABLE.getColumns();
    }
    private List<TraceTreeNode> stack = new ArrayList<TraceTreeNode>();
    private long rootTotalDuration;

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        TraceTreeNode node = stack.get(rowIndex);
        MethodKey methodKey = node.getMethodKey();
        if (methodKey == null) {
            return "?";
        }
        // yea, an enum would be nice

        if (columnIndex == PACKAGE.getIndex()) {
            return methodKey.getPackageName();
        }
        if (columnIndex == CLASS.getIndex()) {
            return methodKey.getClassName();
        }
        if (columnIndex == METHOD.getIndex()) {
            return methodKey.getMethodName();
        }
        if (columnIndex == SIGNATURE.getIndex()) {
            return methodKey.getSig().getShortText();
        }
        if (columnIndex == ENTERS.getIndex()) {
            return node.getAccumulator().getTotalEnters();
        }
        if (columnIndex == EXITS.getIndex()) {
            return node.getAccumulator().getTotalExits();
        }
        if (columnIndex == ERRORS.getIndex()) {
            return node.getAccumulator().getTotalErrors();
        }
        if (columnIndex == THREADS.getIndex()) {
            return node.getAccumulator().getMaxConcurrentThreads();
        }
        if (columnIndex == TOTAL.getIndex()) {
            return node.getAccumulator().getTotalDuration();
        }
        if (columnIndex == AVERAGE.getIndex()) {
            return node.getAccumulator().getMeanDuration();
        }
        if (columnIndex == TOTAL_METHOD.getIndex()) {
            return node.getTotalSelfDuration();
        }
        if (columnIndex == AVERAGE_METHOD.getIndex()) {
            return node.getMeanSelfDuration();
        }
        if (columnIndex == STANDARD_DEVIATION.getIndex()) {
            return node.getAccumulator().getStdDeviation();
        }
        if (columnIndex == MIN.getIndex()) {
            return node.getAccumulator().getMinDuration();
        }
        if (columnIndex == MAX.getIndex()) {
            return node.getAccumulator().getMaxDuration();
        }
        if (columnIndex == PERCENT_OF_PARENT.getIndex()) {
            return new Percent(node.getPctOfMeanParentDuration());
        }
        if (columnIndex == PERCENT_OF_ROOT.getIndex()) {
            return new Percent(getPctOfAvgRootDuration(node));
        }
        return null;
    }

    public synchronized void setStackTreeNode(TraceTreeNode root, TraceTreeNode node) {
        List<TraceTreeNode> newStack = new ArrayList<TraceTreeNode>();
        TraceTreeNode currNode = node;

        while (currNode.getParent() != null) {
            newStack.add(currNode);
            if (currNode == root)
                break;
            currNode = currNode.getParentNode();
        }

        // -------------------------------------
        // I'm calcing the %of root on the fly - which allows any node to be
        // set as the root node of the view. To do that, I need to know the
        // total
        // duration of the effective root. aka the last node on the stack.
        // this is different from "root" because the that object may be the
        // fake base node.
        if (newStack.isEmpty()) {
            rootTotalDuration = 0;
        } else {
            TraceTreeNode viewRoot = newStack.get(newStack.size() - 1);
            rootTotalDuration = viewRoot.getAccumulator().getTotalDuration();
        }

        stack = newStack;

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return stack.size();
    }

    @Override
    public int getColumnCount() {
        return TABLE.getColumnCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return TABLE.getColumn(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TABLE.getColumn(columnIndex).getType();
    }

    public double getPctOfAvgRootDuration(TraceTreeNode node) {
        return (rootTotalDuration > 0)
                ? ((100.0 * node.getAccumulator().getTotalDuration()) / rootTotalDuration)
                : Double.NaN;
    }
}
