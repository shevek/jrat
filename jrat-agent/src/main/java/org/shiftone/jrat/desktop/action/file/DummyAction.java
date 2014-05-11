package org.shiftone.jrat.desktop.action.file;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DummyAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(DummyAction.class);

    public DummyAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + getValue(NAME) + " - " + e);
    }
}
