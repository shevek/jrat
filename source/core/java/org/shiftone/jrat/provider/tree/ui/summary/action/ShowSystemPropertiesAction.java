package org.shiftone.jrat.provider.tree.ui.summary.action;


import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.SortOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ShowSystemPropertiesAction extends AbstractAction {

    private final Component component;
    private final Properties properties;


    public ShowSystemPropertiesAction(Component component, Properties properties) {
        super("Show System Properties");
        this.component = component;
        this.properties = properties;
    }

    public void actionPerformed(ActionEvent e) {

        JDialog dialog = new TextDialog(properties);

        dialog.setSize(new Dimension(400, 500));
        dialog.setLocationRelativeTo(component);
        dialog.setModal(true);
        dialog.setVisible(true);

    }

    private class TextDialog extends JDialog {

        public TextDialog(Properties properties) {

            DefaultTableModel tableModel = new DefaultTableModel();

            tableModel.addColumn("Name");
            tableModel.addColumn("Value");
            for (Iterator keys = properties.keySet().iterator(); keys.hasNext();) {

                String key = (String) keys.next();
                String value = properties.getProperty(key);

                tableModel.addRow(new Object[]{key, value});
            }

            JXTable table = new JXTable(tableModel);
            table.setSortOrder(0, SortOrder.ASCENDING);


            setLayout(new BorderLayout());
            add(new JScrollPane(table), BorderLayout.CENTER);

            setTitle("System Properties During Run");
            setModal(true);
        }

    }

}
