package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class LicenseAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(LicenseAction.class);

    public LicenseAction() {
        super("Open Source Licenses");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_L));
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);
    }
}
