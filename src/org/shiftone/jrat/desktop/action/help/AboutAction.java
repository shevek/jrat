package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AboutAction  extends AbstractAction {

     private static final Logger LOG = Logger.getLogger(AboutAction.class);

    public AboutAction() {
        super("About JRat");
        putValue(Action.MNEMONIC_KEY,  new Integer(KeyEvent.VK_A));
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);
    }
}
