package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.provider.tree.ui.trace.TreeViewerPanel; 
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Set;
import java.util.Date;

/**
 * @Author Jeff Drost
 */
public class MainViewPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(MainViewPanel.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final StackNode rootNode;
    private final Set allMethodKeys;
    private final long sessionStartMs;
    private final long sessionEndMs;


    public MainViewPanel(StackNode rootNode, Set allMethodKeys, long sessionStartMs, long sessionEndMs) {

        this.rootNode = rootNode;
        this.allMethodKeys = allMethodKeys;
        this.sessionStartMs= sessionStartMs;
        this.sessionEndMs = sessionEndMs;
        
        setLayout(new BorderLayout());

        add(createTitleLanel(), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        LOG.info("creating Trace");
        tabbedPane.addTab("Trace", new TreeViewerPanel(rootNode));
        LOG.info("creating Hierarchy");
        tabbedPane.addTab("Coverage", new JLabel());
        //tabbedPane.addTab("Hierarchy", new GraphViewPanel(rootNode));
    }

    private JLabel createTitleLanel() {
        Date start = new Date(sessionStartMs);
        Date end = new Date(sessionEndMs);

        return new JLabel(start + " to " + end);
    }

}
