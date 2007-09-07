package org.shiftone.jrat.provider.tree.ui.summary;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.shiftone.jrat.provider.tree.ui.summary.action.AllColumnVisibilityAction;
import org.shiftone.jrat.provider.tree.ui.summary.action.ResetColumnVisibilityAction;
import org.shiftone.jrat.provider.tree.ui.summary.action.SortAndShowColumnAction;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SummaryPanel extends JPanel {

    private final JXTable table;
    private final JXTaskPane tasks;
    private final JXTaskPane details;
    private final JXTaskPane summary;

    public SummaryPanel(
            SummaryTableModel summaryTableModel,
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        table = new JXTable();
        table.setModel(summaryTableModel);
        table.setColumnControlVisible(true);
        //table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        table.getSelectionModel().addListSelectionListener(new SelectionListener());

        splitPane.setRightComponent(new JScrollPane(table));

        JXTableWatcher.initialize(
                table,
                Preferences.userNodeForPackage(SummaryPanel.class).node("columns"),
                SummaryTableModel.getColumns());

        PercentTableCellRenderer.setDefaultRenderer(table);

        {
            JXTaskPaneContainer taskPaneContainer = new JXTaskPaneContainer();

            taskPaneContainer.add(tasks = createTasksPane(table));
            taskPaneContainer.add(details = createDetailPane());
            taskPaneContainer.add(summary = createSummaryPane(sessionStartMs, sessionEndMs, systemProperties, hostName, hostAddress));

            splitPane.setLeftComponent(taskPaneContainer);
        }


        setLayout(new BorderLayout());

        add(splitPane, BorderLayout.CENTER);

    }

    private JXTaskPane createDetailPane() {
        JXTaskPane details = new JXTaskPane();
        details.setTitle("Details");
        details.setVisible(false);
        return details;
    }

    private JXTaskPane createTasksPane(JXTable table) {

        JXTaskPane pane = new JXTaskPane();
        pane.setTitle("Tasks");

        pane.add(new JXHyperlink(new SortAndShowColumnAction(
                "Sort by Total Method Duration",
                table,
                SummaryTableModel.TOTAL_METHOD)));

        pane.add(new JXHyperlink(new SortAndShowColumnAction(
                "Sort by Exception Rate",
                table,
                SummaryTableModel.EXCEPTION_RATE)));

        pane.add(new JXHyperlink(
                new ResetColumnVisibilityAction(table, SummaryTableModel.getColumns())
        ));

        pane.add(new JXHyperlink(
                new AllColumnVisibilityAction(table)
        ));

        return pane;
    }

    private JXTaskPane createSummaryPane(
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {

        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);

        // honestly I don't feel great about this, but
        // laying this out is such a pain any other way (that I know).
        JXTaskPane summaryPane = new JXTaskPane();
        summaryPane.setTitle("Session Details");

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
        sb.append(dateFormat.format(new Date(sessionStartMs)));
        sb.append("</td></tr>");

        sb.append("<tr><td>End:</td><td>");
        sb.append(dateFormat.format(new Date(sessionEndMs)));
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


    private class SelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {

            int[] rows = table.getSelectedRows();
            
        }
    }
}
