package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.provider.tree.ui.trace.TreeViewerPanel; 
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.Set;

/**
 * @Author Jeff Drost
 */
public class MainViewPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(MainViewPanel.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final StackNode rootNode;
    private final Set allMethodKeys;

    public MainViewPanel(StackNode rootNode, Set allMethodKeys) {
        this.rootNode = rootNode;
        this.allMethodKeys = allMethodKeys;
        
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        LOG.info("creating Trace");
        tabbedPane.addTab("Trace", new TreeViewerPanel(rootNode));
        LOG.info("creating Hierarchy");
        //tabbedPane.addTab("Hierarchy", new GraphViewPanel(rootNode));
    }

}
