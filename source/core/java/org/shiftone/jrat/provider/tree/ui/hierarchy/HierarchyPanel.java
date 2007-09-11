package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.JXTreeTable;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryTableModel;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryPanel;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class HierarchyPanel extends JPanel {

    private JXTreeTable treeTable;

    public HierarchyPanel(HierarchyTreeTableModel model) {
        treeTable = new JXTreeTable();
        treeTable.setTreeTableModel(model);
        treeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int i = 1; i < treeTable.getColumnCount(); i++) {
            treeTable.getColumn(i).setMaxWidth(160);
        }
        PercentTableCellRenderer.setDefaultRenderer(treeTable);

        treeTable.setTreeCellRenderer(new HierarchyTreeCellRenderer());
        treeTable.setColumnControlVisible(true);
        treeTable.setShowGrid(true);

        JXTableWatcher.initialize(
                treeTable,
                Preferences.userNodeForPackage(HierarchyPanel.class).node("columns"),
                HierarchyTreeTableModel.getColumns());

        setLayout(new BorderLayout());
        add(new JScrollPane(treeTable), BorderLayout.CENTER);

    }
}
