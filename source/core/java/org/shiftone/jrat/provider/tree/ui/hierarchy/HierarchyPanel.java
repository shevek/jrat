package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXTreeTable;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class HierarchyPanel extends JPanel {

    private JXTreeTable treeTable;

    private JXHeader header;

    public HierarchyPanel(HierarchyTreeTableModel model) {

        treeTable = new JXTreeTable();
        treeTable.setTreeTableModel(model);
        for (int i = 1; i < treeTable.getColumnCount(); i++) {
            treeTable.getColumn(i).setMaxWidth(150);
        }
        PercentTableCellRenderer.setDefaultRenderer(treeTable);

        treeTable.setTreeCellRenderer(new HierarchyTreeCellRenderer());
        treeTable.setColumnControlVisible(true);

        header = new JXHeader("Hirarchy View",
                "Execution is tracked at a method level, not at the line level.  " +
                        "A method is identified as covered if it was entered at least once.  " +
                        "");

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(treeTable), BorderLayout.CENTER);

    }
}
