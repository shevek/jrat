package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.util.log.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class ExitAction
 *
 * @author Jeff Drost
 *
 */
public class ExitAction implements ActionListener {

    private static final Logger LOG = Logger.getLogger(ExitAction.class);

    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e) {
        System.exit(1);
    }
}
