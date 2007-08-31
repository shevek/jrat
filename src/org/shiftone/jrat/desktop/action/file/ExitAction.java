package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ExitAction extends AbstractAction {

    public ExitAction() {
        super("Exit");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
