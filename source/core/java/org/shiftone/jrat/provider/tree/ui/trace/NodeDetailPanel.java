package org.shiftone.jrat.provider.tree.ui.trace;


import org.shiftone.jrat.provider.tree.ui.TraceTreeNode; 
import org.shiftone.jrat.provider.tree.ui.trace.stack.StackTableModel;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;


public class NodeDetailPanel extends JPanel {

    private StackTableModel stackTableModel = null;
    private JLabel methodLabel;

    public NodeDetailPanel() {


        methodLabel = new JLabel();

        // ----- [ Stack ] -----
        stackTableModel = new StackTableModel();
        JXTable stackTable = new JXTable(stackTableModel);
        stackTable.setColumnControlVisible(true);
        stackTable.setSortable(false);

          JXTableWatcher.initialize(
                stackTable,
                Preferences.userNodeForPackage(NodeDetailPanel.class).node("columns"),
                StackTableModel.getColumns());

        PercentTableCellRenderer.setDefaultRenderer(stackTable);

        setLayout(new BorderLayout());
        add(new JScrollPane(stackTable), BorderLayout.CENTER);
        add(methodLabel, BorderLayout.NORTH);
    }


    public synchronized void setStackTreeNode(TraceTreeNode root, TraceTreeNode node) {

        Assert.assertNotNull("root", root);
        Assert.assertNotNull("node", node);

        if (node.isRootNode()) {
            methodLabel.setText("");
        } else {
            methodLabel.setText(" " + node.getMethodKey().getShortMethodDescription());
        }

        stackTableModel.setStackTreeNode(root, node);        
    }
}
