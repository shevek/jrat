package org.shiftone.jrat.provider.tree.ui.summary.action;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class AllColumnVisibilityAction extends AbstractAction {

    private final JXTable table;


    public AllColumnVisibilityAction(JXTable table) {
        super("Make All Table Visible");
        this.table = table;
    }


    public void actionPerformed(ActionEvent actionEvent) {
        List columns = table.getColumns(true);
        for (int i = 0; i < columns.size(); i++) {
            TableColumnExt columnExt = (TableColumnExt) columns.get(i);
            columnExt.setVisible(true);
        }
    }
}
