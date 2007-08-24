package org.shiftone.jrat.provider.tree.ui.trace.children;

import ca.odell.glazedlists.gui.TableFormat;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.trace.StackTreeNode;
import org.shiftone.jrat.util.Percent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ChildrenTableFormat implements TableFormat {
    private String[] COLUMN_NAMES =
            {
                    "Class", "Method", "Signature",              //
                    "Enters", "Exits", "Errors",                 //
                    "Threads", "Total ms",                       //
                    "Avg ms", "Std Dev",                         //
                    "Min ms", "Max ms",                          //
                    "%Node",
            };

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public String getColumnName(int i) {
        return COLUMN_NAMES[i];
    }

    public Object getColumnValue(Object object, int columnIndex) {

        StackTreeNode node = (StackTreeNode) object;
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
        }

        return null;
    }
}
