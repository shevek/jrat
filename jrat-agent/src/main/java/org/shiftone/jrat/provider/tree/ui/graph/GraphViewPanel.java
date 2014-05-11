package org.shiftone.jrat.provider.tree.ui.graph;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.TreeNode;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class GraphViewPanel extends JPanel implements ActionListener {

    private static final Logger LOG = Logger.getLogger(GraphViewPanel.class);
    private final TreeNode rootNode;
    private final JButton button = new JButton("push me");

    public GraphViewPanel(TreeNode rootNode) {
        this.rootNode = rootNode;
        addComponentListener(new ComponentListener());
        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        button.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map map = new HashMap();
        processTree(rootNode, map);
    }

    private void processTree(TreeNode node, Map nodes) {
        List children = node.getChildren();
        for (Object children1 : children) {
            TreeNode child = (TreeNode) children1;
            processNode(child, nodes);
            processTree(child, nodes);
        }
    }

    private void processNode(TreeNode treeNode, Map nodes) {
        MethodKey methodKey = treeNode.getMethodKey();
        GraphNode graphNode = getGraphNode(methodKey, nodes);

        List children = treeNode.getChildren();
        for (Object children1 : children) {
            TreeNode child = (TreeNode) children1;
            GraphNode childGraphNode = getGraphNode(child.getMethodKey(), nodes);
            graphNode.addCalled(childGraphNode);
            childGraphNode.addCalledBy(graphNode);
        }

        graphNode.addStackNode(treeNode);
    }

    private GraphNode getGraphNode(MethodKey methodKey, Map nodes) {
        GraphNode node = (GraphNode) nodes.get(methodKey);
        if (node == null) {
            node = new GraphNode(methodKey);
            nodes.put(methodKey, node);
        }
        return node;
    }

    private class ComponentListener extends ComponentAdapter {

        @Override
        public void componentShown(ComponentEvent e) {
            LOG.info("componentShown");
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            LOG.info("componentHidden");
        }
    }
}
