package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DocsAction  extends AbstractAction {

     private static final Logger LOG = Logger.getLogger(DocsAction.class);

    public DocsAction() {
        super("Documentation");
        putValue(Action.MNEMONIC_KEY,  new Integer(KeyEvent.VK_D));
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);
    }
}
