package org.shiftone.jrat.provider.stats.ui;


import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import org.shiftone.jrat.ui.util.NoOpComparator;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Collection;


/**
 * @author Jeff Drost
 */
public class StatsViewerPanel extends JPanel {

    private static final Logger LOG = Logger.getLogger(StatsViewerPanel.class);

    public StatsViewerPanel(Collection methodKeyAccumulators) {

        EventList accumulatorsEventList = new BasicEventList();

        accumulatorsEventList.addAll(methodKeyAccumulators);

        TableFormat tableFormat = new StatsTableFormat();
        StatsMatcherEditor classMatcherEditor = new StatsMatcherEditor(StatsMatcherEditor.FIELD_CLASS);
        StatsMatcherEditor methodMatcherEditor = new StatsMatcherEditor(StatsMatcherEditor.FIELD_METHOD);
        StatsMatcherEditor signatureMatcherEditor = new StatsMatcherEditor(StatsMatcherEditor.FIELD_SIGNATURE);

        accumulatorsEventList = new FilterList(accumulatorsEventList, classMatcherEditor);
        accumulatorsEventList = new FilterList(accumulatorsEventList, methodMatcherEditor);
        accumulatorsEventList = new FilterList(accumulatorsEventList, signatureMatcherEditor);

        TotalsEventList totalsEventList = new TotalsEventList(accumulatorsEventList);
        SortedList sortedAccumulators = new SortedList(accumulatorsEventList, NoOpComparator.INSTANCE);
        JTable accumulatorsTable = new JTable(new EventTableModel(sortedAccumulators, tableFormat));
        JTable totalsTable = new JTable(new EventTableModel(totalsEventList, tableFormat),
                accumulatorsTable.getColumnModel()) {

            public void columnMarginChanged(ChangeEvent e) {

                // LOG.info("columnMarginChanged");
                this.repaint();
            }
        };

        PercentTableCellRenderer.setDefaultRenderer(totalsTable);
        PercentTableCellRenderer.setDefaultRenderer(accumulatorsTable);
        totalsTable.setRowSelectionAllowed(false);
        totalsTable.setColumnSelectionAllowed(false);
        totalsTable.setBackground(new Color(255, 255, 200));
        totalsTable.setGridColor(Color.black);
        totalsTable.setShowGrid(false);

        // totalsTable.setShowVerticalLines(false);
        StatsMatcherEditorsPanel editorsPanel = new StatsMatcherEditorsPanel();

        editorsPanel.add(new StatsMatcherEditorPanel(classMatcherEditor, "Class Name"));
        editorsPanel.add(new StatsMatcherEditorPanel(methodMatcherEditor, "Method"));
        editorsPanel.add(new StatsMatcherEditorPanel(signatureMatcherEditor, "Signature"));

        TableComparatorChooser tableSorter = new TableComparatorChooser(accumulatorsTable, sortedAccumulators, true);

        setLayout(new BorderLayout());
        add(editorsPanel, BorderLayout.NORTH);
        add(new JScrollPane(accumulatorsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants
                .HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        add(totalsTable, BorderLayout.SOUTH);
    }
}
