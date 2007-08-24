package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DummyAction extends AbstractAction  {

    private static final Logger LOG = Logger.getLogger(DummyAction.class);

    public DummyAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed "+ getValue(NAME) + " - " + e);
    }
}
