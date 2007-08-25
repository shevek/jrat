package org.shiftone.jrat.desktop;

import javax.swing.*;
import java.awt.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class View extends JPanel {

    private JTabbedPane tabbedPane;

    public View() {
        setLayout(new BorderLayout());
    }

    public String getTitle() {
        int index = tabbedPane.indexOfComponent(this);
        return tabbedPane.getTitleAt(index);
    }

    public void setTitle(String title) {
        int index = tabbedPane.indexOfComponent(this);
        tabbedPane.setTitleAt(index, title);
    }


    public void setComponent(JComponent component) {

        this.removeAll();

        if (component != null) {
            this.add(component, BorderLayout.CENTER);
        }

    }

    public void remove() {
        tabbedPane.remove(this);
    }
}
