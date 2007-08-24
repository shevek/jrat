package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CloseAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(CloseAction.class);
    private final JTabbedPane tabbedPane;

    public CloseAction(JTabbedPane tabbedPane) {
        super("Close");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_C));
        this.tabbedPane = tabbedPane;
    }

    public void actionPerformed(ActionEvent e) {
        int index = tabbedPane.getSelectedIndex();
        if (index != -1) {
            tabbedPane.remove(index);
        }
    }
}
