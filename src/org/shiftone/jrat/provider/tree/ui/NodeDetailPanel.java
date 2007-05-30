package org.shiftone.jrat.provider.tree.ui;


import org.shiftone.jrat.provider.tree.ui.children.ChildrenPanel;
import org.shiftone.jrat.provider.tree.ui.graph.TreeGraphComponent;
import org.shiftone.jrat.provider.tree.ui.stack.StackTableModel;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.Assert;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.BorderLayout;


public class NodeDetailPanel extends JPanel {

    private StackTreeNode root;
    private StackTreeNode node;
    private JTabbedPane tabbedPane = null;
    private StackTableModel stackTableModel = null;
    private JTable stackTable = null;
    // private ChildrenTableModel childrenTableModel = null;
    // private JTable             childrenTable      = null;
    private JLabel methodLabel;
    private TreeGraphComponent graphComponent;

    private ChildrenPanel childrenPanel;

    // /private PieGraphComponent pieGraphComponent;
    public NodeDetailPanel() {

        tabbedPane = new JTabbedPane();
        methodLabel = new JLabel();

        // ----- [ Stack ] -----
        stackTableModel = new StackTableModel();
        stackTable = new JTable(stackTableModel);

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


    public synchronized void setStackTreeNode(StackTreeNode root, StackTreeNode node) {

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

        // /pieGraphComponent.setStackTreeNode(node);
    }
}
