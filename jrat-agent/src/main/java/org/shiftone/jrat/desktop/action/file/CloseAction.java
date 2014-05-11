package org.shiftone.jrat.desktop.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTabbedPane;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CloseAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(CloseAction.class);
    private final JTabbedPane tabbedPane;

    public CloseAction(JTabbedPane tabbedPane) {
        super("Close");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = tabbedPane.getSelectedIndex();
        if (index != -1) {
            tabbedPane.remove(index);
        }
    }
}
