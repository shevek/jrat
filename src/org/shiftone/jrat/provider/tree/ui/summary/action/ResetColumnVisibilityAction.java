package org.shiftone.jrat.provider.tree.ui.summary.action;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import org.shiftone.jrat.desktop.util.ColumnInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ResetColumnVisibilityAction extends AbstractAction {

    private final JXTable table;
    private final ColumnInfo[] infos;

    public ResetColumnVisibilityAction(JXTable table, ColumnInfo[] infos) {
        super("Reset Default Column Visibility");
        this.table = table;
        this.infos = infos;
    }

    public void actionPerformed(ActionEvent e) {
        List columns = table.getColumns(true);
        for (int i = 0; i < infos.length; i ++) {
            TableColumnExt columnExt = (TableColumnExt) columns.get(i);
            columnExt.setVisible(infos[i].isDefaultVisible());
        }
    }

}
