package org.shiftone.jrat.desktop.action.help;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.shiftone.jrat.desktop.util.Tips;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TipsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(TipsAction.class);
    private final Component component;

    public TipsAction(Component component) {
        super("Tips");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        this.component = component;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tips.show(component, true);

    }
}
