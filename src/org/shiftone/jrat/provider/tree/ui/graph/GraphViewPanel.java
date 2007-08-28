package org.shiftone.jrat.provider.tree.ui.graph;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.TreeNode;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class GraphViewPanel extends JPanel implements ActionListener {
    private static final Logger LOG = Logger.getLogger(GraphViewPanel.class);
    private TreeNode rootNode;
    private JButton button = new JButton("push me");

    public GraphViewPanel(TreeNode rootNode) {
        this.rootNode = rootNode;
        addComponentListener(new ComponentListener());
        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        button.addActionListener(this);

    }


    public void actionPerformed(ActionEvent e) {
        Map map = new HashMap();
        processTree(rootNode, map);
    }

    private void processTree(TreeNode node, Map nodes) {
        List children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            TreeNode child = (TreeNode) children.get(i);
            processNode(child, nodes);
            processTree(child, nodes);
        }
    }

    private void processNode(TreeNode treeNode, Map nodes) {
        MethodKey methodKey = treeNode.getMethodKey();
        GraphNode graphNode = getGraphNode(methodKey, nodes);

        List children = treeNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            TreeNode child = (TreeNode) children.get(i);
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


        public void componentShown(ComponentEvent e) {
            LOG.info("componentShown");
        }

        public void componentHidden(ComponentEvent e) {
            LOG.info("componentHidden");
        }
    }
}
