package org.shiftone.jrat.provider.tree.ui.summary.action;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import org.shiftone.jrat.desktop.util.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ResetColumnVisibilityAction extends AbstractAction {

    private final JXTable table;
    private final List tableColumns;

    public ResetColumnVisibilityAction(JXTable table, List tableColumns) {
        super("Reset Default Column Visibility");
        this.table = table;
        this.tableColumns = tableColumns;
    }

    public void actionPerformed(ActionEvent e) {
        List columns = table.getColumns(true);
        for (int i = 0; i < tableColumns.size(); i++) {
            TableColumnExt columnExt = (TableColumnExt) columns.get(i);
            Table.Column tableColumn = (Table.Column) tableColumns.get(i);
            columnExt.setVisible(tableColumn.isDefaultVisible());
        }
    }

}
