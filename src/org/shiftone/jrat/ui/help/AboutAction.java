package org.shiftone.jrat.ui.help;


import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.ui.util.BackgroundActionListener;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Properties;


/**
 * Class AboutAction
 *
 * @author Jeff Drost
 */
public class AboutAction extends BackgroundActionListener implements UIConstants {

    private static final Logger LOG = Logger.getLogger(AboutAction.class);
    private Frame parent = null;
    private String message = null;

    /**
     * Constructor AboutAction
     *
     * @param parent
     */
    public AboutAction(Frame parent) {

        Properties properties = null;
        StringBuffer sb = new StringBuffer(UI_TITLE);

        //      sb.append(" v" + VersionUtil.getVersion());
        sb.append("\nBy Jeff Drost, Released under the LGPL\n");
        sb.append("Built On " + VersionUtil.getBuiltOn() + " by " + VersionUtil.getBuiltBy() + "\n\n");
        sb.append(WEBSITE + "\n");
        sb.append(EMAIL + "\n");

        message = sb.toString();
        this.parent = parent;
    }


    /**
     * Method actionPerformed
     */
    public void actionPerformedInBackground(ActionEvent e) {

        JOptionPane.showMessageDialog(parent, message, ABOUT_TITLE, JOptionPane.INFORMATION_MESSAGE);

        // JOptionPane.showMessageDialog();
        // JDialog dialog = new ExceptionDialog(parent, "error", "message", new
        // IOException());
        // dialog.setVisible(true);
    }
}
