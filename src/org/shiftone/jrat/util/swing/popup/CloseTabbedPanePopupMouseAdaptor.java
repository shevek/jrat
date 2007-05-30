package org.shiftone.jrat.util.swing.popup;


import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CloseTabbedPanePopupMouseAdaptor extends TabbedPanePopupMouseAdaptor implements ActionListener {

    private JMenuItem closeMenuItem = new JMenuItem("Close");
    private JMenuItem closeAllButMenuItem = new JMenuItem("Close All But This");
    private JMenuItem closeAllMenuItem = new JMenuItem("Close All");

    public CloseTabbedPanePopupMouseAdaptor(JTabbedPane tabPane) {

        super(tabPane);

        popup.add(closeMenuItem);
        popup.add(closeAllButMenuItem);
        popup.add(closeAllMenuItem);
        closeMenuItem.addActionListener(this);
        closeAllButMenuItem.addActionListener(this);
        closeAllMenuItem.addActionListener(this);
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
