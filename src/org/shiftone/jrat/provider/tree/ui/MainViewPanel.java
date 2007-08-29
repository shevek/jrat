package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.ui.hierarchy.HierarchyModelBuilder;
import org.shiftone.jrat.provider.tree.ui.hierarchy.HierarchyPanel;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryPanel;
import org.shiftone.jrat.provider.tree.ui.summary.SummaryTableModel;
import org.shiftone.jrat.provider.tree.ui.trace.TracePanel;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;
import java.util.Set;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MainViewPanel extends JPanel {

    private static final Logger LOG = Logger.getLogger(MainViewPanel.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();

    public MainViewPanel(
            StackTreeNode node,
            Set allMethodKeys,
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {

        setLayout(new BorderLayout());

        //tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        add(tabbedPane, BorderLayout.CENTER);
        {
            SummaryTableModel summaryTableModel = new SummaryTableModel(node);
            tabbedPane.addTab("Summary", new SummaryPanel(summaryTableModel,
                    sessionStartMs, sessionEndMs,
                    systemProperties,
                    hostName, hostAddress));
        }
        {
            tabbedPane.addTab("Trace", new TracePanel(node));
        }
        {
            HierarchyModelBuilder builder = new HierarchyModelBuilder(node, allMethodKeys);
            tabbedPane.addTab("Hierarchy", new HierarchyPanel(builder.getModel()));
        }
    }


}
