package org.shiftone.jrat.provider.tree.ui.summary.action;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class AllColumnVisibilityAction extends AbstractAction {

    private final JXTable table;

    public AllColumnVisibilityAction(JXTable table) {
        super("Make All Columns Visible");
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        List columns = table.getColumns(true);
        for (Object column : columns) {
            TableColumnExt columnExt = (TableColumnExt) column;
            columnExt.setVisible(true);
        }
    }
}
