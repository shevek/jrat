package org.shiftone.jrat.ui.tab;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JTabbedPane;
import java.awt.Graphics;


/**
 * @author Jeff Drost
 */
public class TabbedPaneViewContainer extends JTabbedPane implements ViewContainer {

    private static final Logger LOG = Logger.getLogger(TabbedPaneViewContainer.class);

    public View createView(String title) {

        TabbedView tab = new TabbedView(this, title);

        addTab(title, tab);
        setCurrentView(tab);

        return tab;
    }


    public void removeView(View view) {
        remove((TabbedView) view);
    }


    public void setCurrentView(View view) {
        setSelectedComponent((TabbedView) view);
    }


    public View getCurrentView() {
        return (TabbedView) getSelectedComponent();
    }


    public void paint(Graphics g) {

        // LOG.info("paint");
        super.paint(g);
    }
}
