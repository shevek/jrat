package org.shiftone.jrat.desktop;

import org.shiftone.jrat.util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TabMouseListener extends MouseAdapter implements ActionListener {

    private JMenuItem closeMenuItem = new JMenuItem("Close");
    private JMenuItem closeAllButMenuItem = new JMenuItem("Close All But This");
    private JMenuItem closeAllMenuItem = new JMenuItem("Close All");

    protected JPopupMenu popup = new JPopupMenu();
    protected JTabbedPane tabPane = null;
    protected int index = -1;


    public TabMouseListener(JTabbedPane tabPane) {
        Assert.assertNotNull(tabPane);
        this.tabPane = tabPane;

        popup.add(closeMenuItem);
        popup.add(closeAllButMenuItem);
        popup.add(closeAllMenuItem);

        closeMenuItem.addActionListener(this);
        closeAllButMenuItem.addActionListener(this);
        closeAllMenuItem.addActionListener(this);
    }


    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }


    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }


    private void maybeShowPopup(MouseEvent e) {

        if (e.isPopupTrigger()) {
            index = tabPane.indexAtLocation(e.getX(), e.getY());

            if (index != -1) {
                tabPane.setSelectedIndex(index);
                preShow();
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    protected void preShow() {

        closeMenuItem.setEnabled(true);
        closeAllMenuItem.setEnabled(true);

        if (tabPane.getTabCount() == 1) {
            closeAllButMenuItem.setEnabled(false);
        } else {
            closeAllButMenuItem.setEnabled(true);
        }
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == closeMenuItem) {
            tabPane.remove(index);
        } else if (e.getSource() == closeAllButMenuItem) {
            closeAll(false);
        } else if (e.getSource() == closeAllMenuItem) {
            closeAll(true);
        }
    }


    public void closeAll(boolean includeIndex) {

        for (int i = tabPane.getTabCount() - 1; i >= 0; i--) {
            if ((includeIndex) || (i != index)) {
                tabPane.remove(i);
            }
        }
    }
}
