package org.shiftone.jrat.desktop.action.help;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.desktop.util.BrowserPanel;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DocsAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(DocsAction.class);

    public static final String HOME_URL = "org/shiftone/jrat/desktop/docs/documentation.html";
    public static final String HOME_TITLE = "Documentation";
    private final DesktopFrame desktopFrame;

    public DocsAction(DesktopFrame desktopFrame) {
        super("Documentation");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        this.desktopFrame = desktopFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.info("actionPerformed " + e);

        URL url = getClass().getClassLoader().getResource(HOME_URL);
        Assert.assertNotNull("URL for " + HOME_URL, url);

        desktopFrame.createView("Documentation", new BrowserPanel(url));
    }
}
