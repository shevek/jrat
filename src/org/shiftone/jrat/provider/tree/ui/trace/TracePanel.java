package org.shiftone.jrat.provider.tree.ui.trace;


import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.swing.popup.TreePopupMouseAdaptor;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.*;


/**
 * Class RateViewerPanel
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TracePanel extends JPanel implements TreeSelectionListener, UIConstants {

    private static final Logger LOG = Logger.getLogger(TracePanel.class);
    private static final long serialVersionUID = 1;
    private JSplitPane splitPane = null;
    private JTree tree = null;
    private StackTreeNode rootNode = null;
    private JMenuItem spawnRoot = new JMenuItem(MENU_TREE_SPAWN_ROOT);
    private JMenuItem statView = new JMenuItem(MENU_TREE_STAT_VIEW);
    private JMenuItem touchGraph = new JMenuItem(MENU_TREE_TOUCHGRAPH);
    private NodeDetailPanel detailPanel;

    public TracePanel(StackTreeNode rootNode) {


        this.rootNode = rootNode;
        tree = new JTree(rootNode);
        detailPanel = new NodeDetailPanel();
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        splitPane.setDividerLocation(0.75);
        splitPane.setResizeWeight(0.75);
        splitPane.setOneTouchExpandable(true);
        splitPane.add(new JScrollPane(tree), JSplitPane.TOP);
        splitPane.add(detailPanel, JSplitPane.BOTTOM);
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        //
        tree.addTreeSelectionListener(this);
        tree.setCellRenderer(new StackTreeCellRenderer());
        tree.setScrollsOnExpand(true);
        tree.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        //
        JPopupMenu popupMenu = new JPopupMenu();

        popupMenu.add(statView);
        popupMenu.add(spawnRoot);
        popupMenu.add(touchGraph);

        TreePopupMouseAdaptor treePopupMouseAdaptor = new TreePopupMouseAdaptor(popupMenu, tree);

        tree.addMouseListener(treePopupMouseAdaptor);
        //  statView.addActionListener(new StatsViewAction(treePopupMouseAdaptor));
        // spawnRoot.addActionListener(new SpawnRootAction(treePopupMouseAdaptor));
        // touchGraph.addActionListener(new TouchGraphAction(treePopupMouseAdaptor));
    }


    public void paint(Graphics g) {
        LOG.debug("paint");
        super.paint(g);
    }


    public void valueChanged(TreeSelectionEvent e) {

        TreePath treePath = e.getNewLeadSelectionPath();
        StackTreeNode thisNode = null;

        if (treePath != null) {
            thisNode = (StackTreeNode) treePath.getLastPathComponent();
        }

        final StackTreeNode finalThisNode = thisNode;

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                detailPanel.setStackTreeNode(rootNode, finalThisNode);
            }
        });
    }
}
