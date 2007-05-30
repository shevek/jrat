package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.util.log.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class RemoveCurrentTabAction
 *
 * @author Jeff Drost
 *
 */
public class RemoveCurrentTabAction implements ActionListener {

    private static final Logger LOG           = Logger.getLogger(RemoveCurrentTabAction.class);
    private ViewContainer       viewContainer = null;

    public RemoveCurrentTabAction(ViewContainer viewContainer) {
        this.viewContainer = viewContainer;
    }


    public void actionPerformed(ActionEvent e) {
        viewContainer.removeView(viewContainer.getCurrentView());
    }
}
