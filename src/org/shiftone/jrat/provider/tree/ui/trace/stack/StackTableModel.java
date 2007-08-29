package org.shiftone.jrat.provider.tree.ui.trace.stack;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Class StackTableModel
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class StackTableModel extends AbstractTableModel {

    private static final Logger LOG = Logger.getLogger(StackTableModel.class);
    private List stack = new ArrayList();
    private long rootTotalDuration;
    private String[] COLUMN_NAMES =
            {
                    "Class", "Method", "Signature",              //
                    "Enters", "Exits", "Errors",                 //
                    "Threads", "Total ms",                       //
                    "Avg ms", "Std Dev",                         //
                    "Min ms", "Max ms",                          //
                    "%Parent", "%Root"
            };
    private Class[] COLUMN_TYPES =
            {
                    String.class, String.class, String.class,    //
                    Long.class, Long.class, Long.class,          //
                    Integer.class, Long.class,                   //
                    Float.class, Double.class,                   //
                    Long.class, Long.class,                      //
                    Percent.class, Percent.class
            };

    public synchronized void setStackTreeNode(TraceTreeNode root, TraceTreeNode node) {

        List newStack = new ArrayList();
        TraceTreeNode currNode = node;

        while (currNode.getParent() != null) {
            newStack.add(currNode);

            if (currNode == root) {
                break;
            }

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
            TraceTreeNode viewRoot = (TraceTreeNode) newStack.get(newStack.size() - 1);

            rootTotalDuration = viewRoot.getTotalDuration();
        }

        stack = newStack;

        fireTableDataChanged();
    }


    public int getRowCount() {
        return stack.size();
    }


    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }


    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }


    public Class getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }


    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {

        TraceTreeNode node = (TraceTreeNode) stack.get(rowIndex);
        MethodKey methodKey = node.getMethodKey();

        if (methodKey == null) {
            return "?";
        }

        switch (columnIndex) {

            case 0:
                return methodKey.getClassName();

            case 1:
                return methodKey.getMethodName();

            case 2:
                return methodKey.getPrettySignature();

            case 3:
                return new Long(node.getTotalEnters());

            case 4:
                return new Long(node.getTotalExits());

            case 5:
                return new Long(node.getTotalErrors());

            case 6:
                return new Integer(node.getMaxConcurrentThreads());

            case 7:
                return new Long(node.getTotalDuration());

            case 8:
                return node.getAverageDuration();

            case 9:
                return node.getStdDeviation();

            case 10:
                return new Long(node.getMinDuration());

            case 11:
                return new Long(node.getMaxDuration());

            case 12:
                return new Percent(node.getPctOfAvgParentDuration());

            case 13:
                return new Percent(getPctOfAvgRootDuration(node));
        }

        return null;
    }


    public double getPctOfAvgRootDuration(TraceTreeNode node) {

        return (rootTotalDuration > 0)
                ? ((100.0 * node.getTotalDuration()) / rootTotalDuration)
                : 0;
    }
}
