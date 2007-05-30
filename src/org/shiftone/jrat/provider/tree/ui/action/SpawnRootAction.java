package org.shiftone.jrat.provider.tree.ui.action;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.provider.tree.ui.TreeViewerPanel;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.util.swing.popup.TreePopupMouseAdaptor;

import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SpawnRootAction implements ActionListener, UIConstants {

    private TreePopupMouseAdaptor treePopupMouseAdaptor;
    private View view;

    public SpawnRootAction(TreePopupMouseAdaptor treePopupMouseAdaptor, View view) {
        this.treePopupMouseAdaptor = treePopupMouseAdaptor;
        this.view = view;
    }


    public void actionPerformed(ActionEvent e) {

        TreePath treePath = treePopupMouseAdaptor.getTreePath();
        StackTreeNode nodeModel = (StackTreeNode) treePath.getLastPathComponent();
        String newViewTitle;

        if (nodeModel.isRootNode()) {
            newViewTitle = view.getTitle() + " : Root Node";
        } else {
            newViewTitle = view.getTitle() + " : " + nodeModel.getMethodKey().toString();
        }

        View newView = view.getContainer().createView(newViewTitle);
        TreeViewerPanel treeViewerPanel = new TreeViewerPanel(nodeModel, newView);

        newView.setBody(treeViewerPanel);
    }
}
