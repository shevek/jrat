package org.shiftone.jrat.provider.trace.ui;


import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import org.shiftone.jrat.ui.util.NoOpComparator;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.util.Date;


/**
 * @author Jeff Drost
 *
 */
public class TraceViewPanel extends JPanel implements SwingConstants {

    private static final Logger LOG = Logger.getLogger(TraceViewPanel.class);
    private JTree               traceTree;
    private JLabel              traceLabel;
    private JTable              blackListTable;
    private JLabel              blackListLabel;

    public TraceViewPanel(TraceModel traceModel) {

        EventList methodList = new BasicEventList();

        methodList.addAll(traceModel.getMethodKeyBlackList());

        SortedList sortedMethodList = new SortedList(methodList, NoOpComparator.INSTANCE);
        TableModel tableModel       = new EventTableModel(sortedMethodList, new MethodKeyTableFormat());

        blackListTable = new JTable();

        blackListTable.setModel(tableModel);
        new TableComparatorChooser(blackListTable, sortedMethodList, true);

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.BOTTOM, JTabbedPane.WRAP_TAB_LAYOUT);

        traceTree = new JTree(traceModel.getRootNode());

        traceTree.setCellRenderer(new TraceTreeCellRenderer());
        traceTree.setRootVisible(false);
        traceTree.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        blackListLabel = new JLabel("These " + sortedMethodList.size() + " method(s) were filtered"
                                    + " from the traceTree results because" + " they were executed too many times");
        traceLabel     = new JLabel("This traceTree was captured " + new Date(traceModel.getRunTime()) + ".");

        JPanel blackList = new JPanel();

        blackList.setLayout(new BorderLayout());
        blackList.add(blackListLabel, BorderLayout.NORTH);
        blackList.add(new JScrollPane(blackListTable), BorderLayout.CENTER);

        JPanel trace = new JPanel();

        trace.setLayout(new BorderLayout());
        trace.add(traceLabel, BorderLayout.NORTH);
        trace.add(new JScrollPane(traceTree), BorderLayout.CENTER);
        tabbedPane.add("Thread Trace", trace);
        tabbedPane.add("Excluded Method(s)", blackList);
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }
}
