package org.shiftone.jrat.provider.tree.ui.trace.children;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.ui.util.NoOpComparator;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ChildrenPanel extends JPanel {

    private TableFormat tableFormat = new ChildrenTableFormat();
    private EventList eventList = new BasicEventList();
    private SortedList sortedList = new SortedList(eventList, NoOpComparator.INSTANCE);
    private EventTableModel eventTableModel = new EventTableModel(sortedList, tableFormat);
    private JTable table = new JTable(eventTableModel);
    private JScrollPane scrollPane = new JScrollPane(table);

    public ChildrenPanel() {

        new TableComparatorChooser(table, sortedList, true);
        PercentTableCellRenderer.setDefaultRenderer(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);


    }

    public synchronized void setStackTreeNode(TraceTreeNode node) {

        List newChildren = new ArrayList();
        for (int i = 0; i < node.getChildCount(); i++) {
            TraceTreeNode child = (TraceTreeNode) node.getChildAt(i);
            newChildren.add(child);
        }

        GlazedLists.replaceAll(eventList, newChildren, false);
    }
}
