package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.HierarchyModelBuilder;
import org.shiftone.jrat.provider.tree.ui.hierarchy.HierarchyPanel;
import org.shiftone.jrat.provider.tree.ui.trace.TracePanel;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryPanel;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryTableModel;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.desktop.util.Preferences;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Set;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MainViewPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(MainViewPanel.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final StackNode rootNode;
    private final Set allMethodKeys;

    public MainViewPanel(
            StackNode rootNode,
            Set allMethodKeys,
            long sessionStartMs,
            long sessionEndMs,
            String hostName,
            String hostAddress) {

        this.rootNode = rootNode;
        this.allMethodKeys = allMethodKeys;

        setLayout(new BorderLayout());

        //tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        add(tabbedPane, BorderLayout.CENTER);

        SummaryTableModel summaryTableModel = new SummaryTableModel(rootNode);
        tabbedPane.addTab("Summary", new SummaryPanel( summaryTableModel,
                sessionStartMs,  sessionEndMs,  hostName,  hostAddress));

        tabbedPane.addTab("Trace", new TracePanel(rootNode));

        LOG.info("creating Hierarchy");


        HierarchyModelBuilder builder = new HierarchyModelBuilder(rootNode, allMethodKeys);
        tabbedPane.addTab("Hierarchy", new HierarchyPanel(builder.getModel()));

        //tabbedPane.addTab("Hierarchy", new GraphViewPanel(rootNode));
    }



}
