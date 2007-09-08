package org.shiftone.jrat.provider.tree.ui.summary;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.shiftone.jrat.desktop.util.JXTableWatcher;
import org.shiftone.jrat.desktop.util.Table;
import org.shiftone.jrat.provider.tree.ui.summary.action.AllColumnVisibilityAction;
import org.shiftone.jrat.provider.tree.ui.summary.action.ResetColumnVisibilityAction;
import org.shiftone.jrat.provider.tree.ui.summary.action.SortAndShowColumnAction;
import org.shiftone.jrat.provider.tree.ui.summary.action.ShowSystemPropertiesAction;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private final JLabel detailLabel;
    private final SummaryTableModel summaryTableModel;
    private final long totalMethodDuration;


    public SummaryPanel(
            SummaryTableModel summaryTableModel,
            long totalMethodDuration,
            long sessionStartMs,
            long sessionEndMs,
            Properties systemProperties,
            String hostName,
            String hostAddress) {

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        this.summaryTableModel = summaryTableModel;
        this.totalMethodDuration = totalMethodDuration;

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

            detailLabel = new JLabel();

            JXTaskPaneContainer taskPaneContainer = new JXTaskPaneContainer();

            taskPaneContainer.add(tasks = createTasksPane(table));
            taskPaneContainer.add(details = createDetailPane(detailLabel));
            taskPaneContainer.add(summary = createSummaryPane(sessionStartMs, sessionEndMs, systemProperties, hostName, hostAddress));

            splitPane.setLeftComponent(taskPaneContainer);
        }


        setLayout(new BorderLayout());

        add(splitPane, BorderLayout.CENTER);

    }

    private JXTaskPane createDetailPane(Component component) {
        JXTaskPane details = new JXTaskPane();
        details.setVisible(false);
        details.add(component);
        //details.add(new JXHyperlink(new ExportToCsvAction()));
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
        JXTaskPane pane = new JXTaskPane();
        pane.setTitle("Session Details");


        StringBuffer sb = new StringBuffer("<html><table>");

        sb.append("<tr><td>Start</td><td>");
        sb.append(dateFormat.format(new Date(sessionStartMs)));
        sb.append("</td></tr>");

        sb.append("<tr><td>End</td><td>");
        sb.append(dateFormat.format(new Date(sessionEndMs)));
        sb.append("</td></tr>");

        sb.append("<tr><td>Duration</td><td>");
        sb.append(sessionEndMs - sessionStartMs + " ms");
        sb.append("</td></tr>");

        sb.append("<tr><td>Host</td><td>");
        sb.append(hostName);
        sb.append("</td></tr>");

        sb.append("<tr><td>Address</td><td>");
        sb.append(hostAddress);
        sb.append("</td></tr>");


        sb.append("</table></html>");
        pane.add(new JLabel(sb.toString()));


         pane.add(new JXHyperlink(
                new ShowSystemPropertiesAction(this, systemProperties)
        ));

        return pane;
    }


    private class SelectionListener implements ListSelectionListener {

        private final NumberFormat percentFormat;

        public SelectionListener() {
            percentFormat = DecimalFormat.getPercentInstance();
            percentFormat.setMinimumFractionDigits(1);
        }

        public void valueChanged(ListSelectionEvent e) {

            if (!e.getValueIsAdjusting()) {
                int[] rows = table.getSelectedRows();

                if (rows.length == 0) {
                    hide();
                } else {
                    show(rows);
                }
            }
        }

        private void hide() {
            details.setVisible(false);
        }

        private void show(int[] rows) {

            long methodTime = getTotal(rows, SummaryTableModel.TOTAL_METHOD);
            long totalErrors = getTotal(rows, SummaryTableModel.EXCEPTIONS);
            long totalExists = getTotal(rows, SummaryTableModel.EXITS);
            long uncompleted = getTotal(rows, SummaryTableModel.UNCOMPLETED);

            StringBuffer sb = new StringBuffer("<html><table>");

            sb.append("<tr><td>Total Exits</td><td>");
            sb.append(totalExists);
            sb.append("</td></tr>");

            sb.append("<tr><td>Method Time</td><td>");
            sb.append(methodTime);
            sb.append("ms (");
            sb.append(percentFormat.format((double) methodTime / (double) totalMethodDuration));
            sb.append(")</td></tr>");

            sb.append("<tr><td>Exceptions</td><td>");
            sb.append(totalErrors);
            sb.append("</td></tr>");

            if (totalExists > 0) {
                sb.append("<tr><td>Exception Rate</td><td>");
                sb.append(percentFormat.format((double) totalErrors / (double) totalExists));
                sb.append("</td></tr>");
            }

            if (uncompleted > 0) {
                sb.append("<tr><td>Uncompleted</td><td>");
                sb.append(uncompleted);
                sb.append("</td></tr>");
            }


            if (rows.length == 1) {
                details.setTitle(getMethod(rows[0]));
            } else {
                details.setTitle(rows.length + " methods selected");
            }

            sb.append("</table></html>");
            details.setVisible(true);
            detailLabel.setText(sb.toString());
        }


        private String getMethod(int row) {
            int r = table.convertRowIndexToModel(row);
            return (String) summaryTableModel.getValueAt(r, SummaryTableModel.METHOD.getIndex());
        }

        private long getTotal(int[] rows, Table.Column column) {

            long value = 0;
            for (int i = 0; i < rows.length; i++) {
                int r = table.convertRowIndexToModel(rows[i]);
                Long v = (Long) summaryTableModel.getValueAt(r, column.getIndex());
                if (v != null) {
                    value += v.longValue();
                }
            }
            return value;
        }
    }
}
