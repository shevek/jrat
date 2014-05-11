package org.shiftone.jrat.provider.tree.ui.trace;

import java.awt.BorderLayout;
import java.util.prefs.Preferences;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.provider.tree.ui.trace.stack.StackTableModel;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.Assert;

public class NodeDetailPanel extends JPanel {

    private StackTableModel stackTableModel = null;
    private final JLabel methodLabel;

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
