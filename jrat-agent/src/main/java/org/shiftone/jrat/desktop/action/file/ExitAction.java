package org.shiftone.jrat.desktop.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ExitAction extends AbstractAction {

    public ExitAction() {
        super("Exit");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
