package org.shiftone.jrat.provider.tree.ui.graph;

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
    private JButton button = new JButton("push me");

    public GraphViewPanel(StackNode rootNode) {
        this.rootNode = rootNode;
        addComponentListener(new ComponentListener());
        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        button.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
       GraphBuilder builder = new GraphBuilder();
       Map map = builder.buildGraph(rootNode);
       LOG.info(map.size());
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
