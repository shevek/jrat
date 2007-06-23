package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @Author Jeff Drost
 */
public class ExitAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(ExitAction.class);

    public ExitAction() {
        super("Exit");
        putValue(Action.MNEMONIC_KEY,  new Integer(KeyEvent.VK_X));        
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
