package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;

import java.awt.*;

import javax.swing.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class HierarchyViewPanel extends JPanel {

    private JXTreeTable treeTable;


    public HierarchyViewPanel(HierarchyTreeTableModel model) {

        treeTable = new JXTreeTable();
      treeTable.setTreeTableModel(model);

        setLayout(new BorderLayout());
        add(new JScrollPane(treeTable), BorderLayout.CENTER);

    }
}
