package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DocsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(DocsAction.class);

    public DocsAction() {
        super("Documentation");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_D));
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);
    }
}
