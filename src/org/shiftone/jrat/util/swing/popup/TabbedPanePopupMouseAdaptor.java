package org.shiftone.jrat.util.swing.popup;


import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TabbedPanePopupMouseAdaptor extends MouseAdapter {

    protected JPopupMenu  popup   = new JPopupMenu();
    protected JTabbedPane tabPane = null;
    protected int         index   = -1;

    public TabbedPanePopupMouseAdaptor(JTabbedPane tabPane) {
        this.tabPane = tabPane;
    }


    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }


    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }


    protected void preShow() {}


    private void maybeShowPopup(MouseEvent e) {

        if (e.isPopupTrigger())
        {
            index = tabPane.indexAtLocation(e.getX(), e.getY());

            if (index != -1)
            {
                tabPane.setSelectedIndex(index);
                preShow();
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
