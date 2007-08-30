package org.shiftone.jrat.provider.tree.ui.trace;


import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryTableModel;
import org.shiftone.jrat.provider.tree.ui.trace.children.ChildrenPanel;
import org.shiftone.jrat.provider.tree.ui.trace.graph.TreeGraphComponent;
import org.shiftone.jrat.provider.tree.ui.trace.stack.StackTableModel;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;


public class NodeDetailPanel extends JPanel {

    private TraceTreeNode root;
    private TraceTreeNode node;
    private JTabbedPane tabbedPane = null;
    private StackTableModel stackTableModel = null;
    private JXTable stackTable = null;
    private JLabel methodLabel;
    private TreeGraphComponent graphComponent;

    private ChildrenPanel childrenPanel;

    public NodeDetailPanel() {

        tabbedPane = new JTabbedPane();
        methodLabel = new JLabel();

        // ----- [ Stack ] -----
        stackTableModel = new StackTableModel();
        stackTable = new JXTable(stackTableModel);
        stackTable.setColumnControlVisible(true);
        stackTable.setSortable(false);
// TODO        
//          JXTableWatcher.initialize(
//                stackTable,
//                Preferences.userNodeForPackage(NodeDetailPanel.class).node("columns"),
//                SummaryTableModel.getColumnInfos());

        tabbedPane.add("Call Stack", new JScrollPane(stackTable));

        // ----- [ Children ] -----
        childrenPanel = new ChildrenPanel();
        tabbedPane.add("Children", childrenPanel);

        // ----- [ Graph ] -----
        graphComponent = new TreeGraphComponent();
        tabbedPane.add("Graph", new JScrollPane(graphComponent));

        // /pieGraphComponent = new PieGraphComponent();
        // /tabbedPane.add("Pie", pieGraphComponent);
        PercentTableCellRenderer.setDefaultRenderer(stackTable);
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(methodLabel, BorderLayout.NORTH);
    }


    public synchronized void setStackTreeNode(TraceTreeNode root, TraceTreeNode node) {

        Assert.assertNotNull("root", root);
        Assert.assertNotNull("node", node);

        if (node.isRootNode()) {
            methodLabel.setText("");
        } else {
            methodLabel.setText(" " + node.getMethodKey().toString());
        }

        stackTableModel.setStackTreeNode(root, node);
        graphComponent.setStackTreeNode(node);
        childrenPanel.setStackTreeNode(node);
    }
}
