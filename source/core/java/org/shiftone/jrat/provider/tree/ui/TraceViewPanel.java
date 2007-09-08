package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.ui.hierarchy.HierarchyModelBuilder;
import org.shiftone.jrat.provider.tree.ui.hierarchy.HierarchyPanel;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;
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
public class TraceViewPanel extends JPanel {

    private static final Logger LOG = Logger.getLogger(TraceViewPanel.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();

    public TraceViewPanel(
            TraceTreeNode node,
            Set allMethodKeys,
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {

        setLayout(new BorderLayout());

        //tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        add(tabbedPane, BorderLayout.CENTER);

        long start = System.currentTimeMillis();

        MethodSummaryModel methodSummaryModel = new MethodSummaryModel(node);

        {
            SummaryTableModel summaryTableModel = new SummaryTableModel(methodSummaryModel);
            tabbedPane.addTab("Summary",
                    new SummaryPanel(summaryTableModel,
                    methodSummaryModel.getTotalMethodDuration(),
                    sessionStartMs, sessionEndMs,
                    systemProperties,
                    hostName, hostAddress));
        }
        {
            tabbedPane.addTab("Trace", new TracePanel(node));
        }
        {
            HierarchyModelBuilder builder = new HierarchyModelBuilder(methodSummaryModel, allMethodKeys);
            tabbedPane.addTab("Hierarchy", new HierarchyPanel(builder.getModel()));
        }

        LOG.info("loaded in " + (System.currentTimeMillis() - start) + " ms");

    }


}
