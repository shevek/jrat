package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CloseAllAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(CloseAllAction.class);
    private final JTabbedPane tabbedPane;

    public CloseAllAction(JTabbedPane tabbedPane) {
        super("Close All");
        this.tabbedPane = tabbedPane;
    }

    public void actionPerformed(ActionEvent e) {
        tabbedPane.removeAll();
    }
}
