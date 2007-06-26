package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Jeff Drost
 */
public class GraphViewPanel extends JPanel implements ActionListener {
    private static final Logger LOG = Logger.getLogger(GraphViewPanel.class);
    private StackNode rootNode;
    private JButton button = new JButton();
    private Map nodes = new HashMap();

    public GraphViewPanel(StackNode rootNode) {
        this.rootNode = rootNode;
        addComponentListener(new ComponentListener());
        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        button.addActionListener(this);

    }


    public void actionPerformed(ActionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void processTree(StackNode node) {
        List children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            StackNode child = (StackNode) children.get(i);
            processNode(child);
            processTree(node);
        }
    }

    private void processNode(StackNode stackNode) {
        MethodKey methodKey = stackNode.getMethodKey();
        GraphNode graphNode = getGraphNode(methodKey);

        List children = stackNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            StackNode child = (StackNode) children.get(i);
            GraphNode childGraphNode = getGraphNode(child.getMethodKey());

            graphNode.addCalled(childGraphNode);
            childGraphNode.addCalledBy(graphNode);
        }

        graphNode.addStackNode(stackNode);
    }

    private GraphNode getGraphNode(MethodKey methodKey) {
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
