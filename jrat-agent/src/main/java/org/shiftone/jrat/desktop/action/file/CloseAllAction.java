package org.shiftone.jrat.desktop.action.file;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;
import org.shiftone.jrat.util.log.Logger;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        tabbedPane.removeAll();
    }
}
