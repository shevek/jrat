package org.shiftone.jrat.provider.tree.ui.summary;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryPanel extends JPanel {

    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private final JXTaskPaneContainer taskPaneContainer = new JXTaskPaneContainer();

    public SummaryPanel(SummaryTableModel summaryTableModel, long sessionStartMs, long sessionEndMs, String hostName, String hostAddress) {

        taskPaneContainer.add(createSummaryPane( sessionStartMs,  sessionEndMs,  hostName,  hostAddress));

        JXTable table = new JXTable();
        table.setModel(summaryTableModel);
        table.setColumnControlVisible(true);
        table.getColumnExt(3).setVisible(false);
        table.getColumnExt(4).setVisible(false);


        splitPane.setLeftComponent(taskPaneContainer);
        splitPane.setRightComponent(new JScrollPane(table));

        setLayout(new BorderLayout());

        add(splitPane, BorderLayout.CENTER);

    }

    private JXTaskPane createSummaryPane(long sessionStartMs, long sessionEndMs, String hostName, String hostAddress) {
        // honestly I don't feel great about this, but
        // laying this out is such a pain any other way (that I know).
        JXTaskPane summaryPane = new JXTaskPane();
        summaryPane.setTitle("Session");

        StringBuffer sb = new StringBuffer();
        sb.append("<html><table>");

        sb.append("<tr><td>Start:</td><td>");
        sb.append(new Date(sessionStartMs));
        sb.append("</td></tr>");

        sb.append("<tr><td>End:</td><td>");
        sb.append(new Date(sessionEndMs));
        sb.append("</td></tr>");

        sb.append("<tr><td>Duration:</td><td>");
        sb.append(sessionEndMs - sessionStartMs + " ms");
        sb.append("</td></tr>");

        sb.append("<tr><td>Host:</td><td>");
        sb.append(hostName);
        sb.append("</td></tr>");

        sb.append("<tr><td>Address:</td><td>");
        sb.append(hostAddress);
        sb.append("</td></tr>");

        sb.append("</table></html>");
        summaryPane.add(new JLabel(sb.toString()));

        return summaryPane;
    }

}
