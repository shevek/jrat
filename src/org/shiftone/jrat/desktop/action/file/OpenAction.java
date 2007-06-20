package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.Action;
import javax.swing.AbstractAction;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/**
 * @Author Jeff Drost
 */
public class OpenAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(OpenAction.class);

    public OpenAction() {
        super("Open Session");
        putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_O);
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);
    }
}
