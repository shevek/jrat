package org.shiftone.jrat.util.swing.popup;


import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TreePopupMouseAdaptor extends MouseAdapter {

    private JPopupMenu popup = null;
    private JTree tree = null;
    private TreePath treePath = null;

    public TreePopupMouseAdaptor(JPopupMenu popup, JTree tree) {
        this.popup = popup;
        this.tree = tree;
    }


    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }


    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }


    public JTree getTree() {
        return tree;
    }


    public TreePath getTreePath() {
        return treePath;
    }


    private void maybeShowPopup(MouseEvent e) {

        if (e.isPopupTrigger()) {
            treePath = tree.getPathForLocation(e.getX(), e.getY());

            if (treePath != null) {
                tree.setSelectionPath(treePath);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
