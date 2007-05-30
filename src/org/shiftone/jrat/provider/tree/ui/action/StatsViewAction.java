package org.shiftone.jrat.provider.tree.ui.action;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.provider.stats.ui.StatsViewerPanel;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.util.swing.popup.TreePopupMouseAdaptor;

import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class StatsViewAction implements ActionListener, UIConstants {

    private TreePopupMouseAdaptor treePopupMouseAdaptor;
    private View view;

    public StatsViewAction(TreePopupMouseAdaptor treePopupMouseAdaptor, View view) {
        this.treePopupMouseAdaptor = treePopupMouseAdaptor;
        this.view = view;
    }


    public void actionPerformed(ActionEvent e) {

        TreePath treePath = treePopupMouseAdaptor.getTreePath();
        StackTreeNode nodeModel = (StackTreeNode) treePath.getLastPathComponent();
        Map map = new HashMap();

        populateTable(nodeModel, map);

        try {
            StatsViewerPanel statsViewerPanel = new StatsViewerPanel(map.values());

            //
            String newViewTitle;

            if (nodeModel.isRootNode()) {
                newViewTitle = view.getTitle() + " : Flattened View";
            } else {
                newViewTitle = view.getTitle() + " : " + nodeModel.getMethodKey().toString() + " : Flattened View";
            }

            View newView = view.getContainer().createView(newViewTitle);

            newView.setBody(statsViewerPanel);
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }


    public void populateTable(StackTreeNode nodeModel, Map map) {

        if (!nodeModel.isRootNode()) {

            //
            MethodKey methodKey = nodeModel.getMethodKey();
            MethodKeyAccumulator accumulator = (MethodKeyAccumulator) map.get(methodKey);

            if (accumulator == null) {
                accumulator = new MethodKeyAccumulator(methodKey);

                map.put(methodKey, accumulator);
            }

            accumulator.combine(nodeModel);
        }

        for (int i = 0; i < nodeModel.getChildCount(); i++) {
            populateTable(nodeModel.getStackTreeNodeAt(i), map);
        }
    }
}
