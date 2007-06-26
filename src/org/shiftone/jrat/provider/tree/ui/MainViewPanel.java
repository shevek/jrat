package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.provider.tree.ui.trace.TreeViewerPanel;
import org.shiftone.jrat.provider.tree.ui.graph.GraphViewPanel;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

/**
 * @Author Jeff Drost
 */
public class MainViewPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(MainViewPanel.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final StackNode rootNode;

    public MainViewPanel(StackNode rootNode) {
        this.rootNode = rootNode;
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        LOG.info("creating Trace");
        tabbedPane.addTab("Trace", new TreeViewerPanel(rootNode));
        LOG.info("creating Hierarchy");
        tabbedPane.addTab("Hierarchy", new GraphViewPanel(rootNode));
    }

}
