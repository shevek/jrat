package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @Author Jeff Drost
 */
public class CloseAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(CloseAction.class);

    public CloseAction() {
        super("Close");
        putValue(Action.MNEMONIC_KEY,  KeyEvent.VK_C);        
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);
    }
}
