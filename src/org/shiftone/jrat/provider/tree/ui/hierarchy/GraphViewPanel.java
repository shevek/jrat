package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;
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
    private StackNode rootNode;
    private JButton button = new JButton("push me");

    public GraphViewPanel(StackNode rootNode) {
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

    private void processTree(StackNode node, Map nodes) {
        List children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            StackNode child = (StackNode) children.get(i);
            processNode(child, nodes);
            processTree(child, nodes);
        }
    }

    private void processNode(StackNode stackNode, Map nodes) {
        MethodKey methodKey = stackNode.getMethodKey();
        GraphNode graphNode = getGraphNode(methodKey, nodes);

        List children = stackNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            StackNode child = (StackNode) children.get(i);
            GraphNode childGraphNode = getGraphNode(child.getMethodKey(), nodes);

            graphNode.addCalled(childGraphNode);
            childGraphNode.addCalledBy(graphNode);
        }

        graphNode.addStackNode(stackNode);
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
