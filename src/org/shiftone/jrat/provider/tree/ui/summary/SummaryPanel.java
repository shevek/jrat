package org.shiftone.jrat.provider.tree.ui.summary;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.shiftone.jrat.desktop.util.Preferences;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Properties;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryPanel extends JPanel {


    public SummaryPanel(
            SummaryTableModel summaryTableModel,
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JXTaskPaneContainer taskPaneContainer = new JXTaskPaneContainer();

        taskPaneContainer.add(createSummaryPane(sessionStartMs, sessionEndMs, systemProperties, hostName, hostAddress));

        JXTable table = new JXTable();
        table.setModel(summaryTableModel);
        table.setColumnControlVisible(true);
        //   table.getColumnExt(3).setVisible(false);
        //  table.getColumnExt(4).setVisible(false);

        JXTableWatcher.initialize(table, Preferences.getPreferences().getSummaryTableVisibility());


        splitPane.setLeftComponent(taskPaneContainer);
        splitPane.setRightComponent(new JScrollPane(table));

        setLayout(new BorderLayout());

        add(splitPane, BorderLayout.CENTER);

    }


    private JXTaskPane createSummaryPane(
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {
        // honestly I don't feel great about this, but
        // laying this out is such a pain any other way (that I know).
        JXTaskPane summaryPane = new JXTaskPane();
        summaryPane.setTitle("Session");

        String userName = systemProperties.getProperty("user.name");
        String javaVersion = systemProperties.getProperty("java.version");
        String javaVendor = systemProperties.getProperty("java.vendor");
        String osName = systemProperties.getProperty("os.name");
        String osArch = systemProperties.getProperty("os.arch");
        String osVersion = systemProperties.getProperty("os.version");

        StringBuffer sb = new StringBuffer();
        sb.append("<html><table>");

        sb.append("<tr><td>User:</td><td>");
        sb.append(userName);
        sb.append("</td></tr>");

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

        sb.append("<tr><td>OS Name:</td><td>");
        sb.append(osName);
        sb.append("</td></tr>");

        sb.append("<tr><td>OS Arch:</td><td>");
        sb.append(osArch);
        sb.append("</td></tr>");

        sb.append("<tr><td>OS Version:</td><td>");
        sb.append(osVersion);
        sb.append("</td></tr>");

        sb.append("<tr><td>Java Vendor:</td><td>");
        sb.append(javaVendor);
        sb.append("</td></tr>");

        sb.append("<tr><td>Java Version:</td><td>");
        sb.append(javaVersion);
        sb.append("</td></tr>");

        sb.append("</table></html>");
        summaryPane.add(new JLabel(sb.toString()));

        return summaryPane;
    }

}
