package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.JXTreeTable;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class HierarchyViewPanel extends JPanel {

    private JXTreeTable treeTable;


    public HierarchyViewPanel(HierarchyTreeTableModel model) {

        treeTable = new JXTreeTable();
        treeTable.setTreeTableModel(model);
        for (int i = 1; i < treeTable.getColumnCount(); i ++) {
            treeTable.getColumn(i).setMaxWidth(150);
        }
        PercentTableCellRenderer.setDefaultRenderer(treeTable);

        treeTable.setTreeCellRenderer(new HierarchyTreeCellRenderer());

        setLayout(new BorderLayout());
        add(new JScrollPane(treeTable), BorderLayout.CENTER);

    }
}
