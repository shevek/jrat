package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.desktop.util.Tips;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TipsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(TipsAction.class);
    private final Component component;

    public TipsAction(Component component) {
        super("Tips");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_T));
        this.component = component;
    }

    public void actionPerformed(ActionEvent e) {
        Tips.show(component, true);

    }
}
