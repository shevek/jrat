package org.shiftone.jrat.desktop.action.help;

import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.desktop.util.BrowserPanel;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DocsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(DocsAction.class);
    private final DesktopFrame desktopFrame;

    public static final String HOME_URL = "org/shiftone/jrat/desktop/docs/documentation.html";
    public static final String HOME_TITLE = "Documentation";

    public DocsAction(DesktopFrame desktopFrame) {
        super("Documentation");
        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_D));
        this.desktopFrame = desktopFrame;
    }

    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);

        URL url = getClass().getClassLoader().getResource(HOME_URL);

        desktopFrame.createView("Documentation", new BrowserPanel(url));
    }
}
