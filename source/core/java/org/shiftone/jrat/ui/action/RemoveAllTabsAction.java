package org.shiftone.jrat.ui.action;


import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.util.log.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class RemoveAllTabsAction
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class RemoveAllTabsAction implements ActionListener {

    private static final Logger LOG = Logger.getLogger(RemoveAllTabsAction.class);
    private ViewContainer viewContainer = null;

    public RemoveAllTabsAction(ViewContainer viewContainer) {
        this.viewContainer = viewContainer;
    }


    public void actionPerformed(ActionEvent e) {
        viewContainer.removeAll();
    }
}
