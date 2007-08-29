package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.desktop.Main;
import org.shiftone.jrat.desktop.util.Tips;
import org.jdesktop.swingx.tips.TipLoader;
import org.jdesktop.swingx.tips.TipOfTheDayModel;
import org.jdesktop.swingx.JXTipOfTheDay;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.prefs.Preferences;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TipsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(LicenseAction.class);
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
