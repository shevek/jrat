package org.shiftone.jrat.desktop.action.help;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AboutAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(AboutAction.class);
    private final Container container;

    public AboutAction(Container container) {
        super("About JRat");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Java Runtime Analysis Toolkit");
        sb.append("\nBy Jeff Drost, Released under the LGPL\n");
        sb.append("Built On ").append(VersionUtil.getBuiltOn()).append(" by ").append(VersionUtil.getBuiltBy()).append("\n\n");

        JOptionPane.showMessageDialog(
                container,
                sb.toString(),
                "About JRat",
                JOptionPane.INFORMATION_MESSAGE);

    }
}
