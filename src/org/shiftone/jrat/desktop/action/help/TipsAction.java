package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;
import org.jdesktop.swingx.tips.TipLoader;
import org.jdesktop.swingx.tips.TipOfTheDayModel;
import org.jdesktop.swingx.tips.DefaultTipOfTheDayModel;
import org.jdesktop.swingx.tips.DefaultTip;
import org.jdesktop.swingx.JXTipOfTheDay;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TipsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(LicenseAction.class);
    private final Component component;
    private TipOfTheDayModel tipOfTheDayModel ;

    public TipsAction(Component component) {
        super("Tips");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_T));
        this.component = component;


        DefaultTipOfTheDayModel model = new DefaultTipOfTheDayModel();
        model.add(new DefaultTip("Plant your corn early", "yea"));
        tipOfTheDayModel = model;
    }

    public void actionPerformed(ActionEvent e) {
        JXTipOfTheDay totd = new JXTipOfTheDay(tipOfTheDayModel);
        totd.showDialog(component);
    }
}
