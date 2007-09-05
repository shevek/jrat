package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.VersionUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AboutAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(AboutAction.class);
    private Container container;

    public AboutAction(Container container) {
        super("About JRat");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
        this.container = container;
    }

    public void actionPerformed(ActionEvent e) {
        StringBuffer sb = new StringBuffer();
        sb.append("Java Runtime Analysis Toolkit");
        sb.append("\nBy Jeff Drost, Released under the LGPL\n");
        sb.append("Built On " + VersionUtil.getBuiltOn() + " by " + VersionUtil.getBuiltBy() + "\n\n");

        JOptionPane.showMessageDialog(
                container,
                sb.toString(),
                "About JRat",
                JOptionPane.INFORMATION_MESSAGE);


    }
}
