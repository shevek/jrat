package org.shiftone.jrat.provider.tree.ui.children;

import ca.odell.glazedlists.gui.TableFormat;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.time.TimeUnit;

/**
 * @author Jeff Drost
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
                return new Long(node.getTotalDuration(TimeUnit.MS));

            case 8:
                return node.getAverageDuration(TimeUnit.MS);

            case 9:
                return node.getStdDeviation();

            case 10:
                return node.getMinDuration(TimeUnit.MS);

            case 11:
                return node.getMaxDuration(TimeUnit.MS);

            case 12:
                return new Percent(node.getPctOfAvgParentDuration());
        }

        return null;
    }
}
