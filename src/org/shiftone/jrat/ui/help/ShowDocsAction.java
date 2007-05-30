package org.shiftone.jrat.ui.help;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.ui.util.BackgroundActionListener;
import org.shiftone.jrat.ui.util.BrowserPanel;
import org.shiftone.jrat.util.log.Logger;

import java.awt.event.ActionEvent;
import java.net.URL;


/**
 * Class ShowDocsAction
 *
 * @author Jeff Drost
 *
 */
public class ShowDocsAction extends BackgroundActionListener {

    private static final Logger     LOG           = Logger.getLogger(ShowDocsAction.class);
    public static final ClassLoader LOADER        = ShowDocsAction.class.getClassLoader();
    public static final String      HOME_URL      = "org/shiftone/jrat/ui/help/html/index.html";
    public static final String      HOME_TITLE    = "Documentation";
    public static final String      LICENSE_URL   = "org/shiftone/jrat/ui/help/html/license/index.html";
    public static final String      LICENSE_TITLE = "JRat Licenses";
    private ViewContainer           viewContainer;
    private String                  title;
    private String                  urlText;

    public ShowDocsAction(ViewContainer viewContainer, String title, String urlText) {

        this.viewContainer = viewContainer;
        this.title         = title;
        this.urlText       = urlText;
    }


    /**
     * Method actionPerformed
     */
    public void actionPerformedInBackground(ActionEvent e) {

        URL          url          = LOADER.getResource(urlText);
        BrowserPanel browserPanel = new BrowserPanel(url);
        View         view         = viewContainer.createView(title);

        view.setBody(browserPanel);
        viewContainer.setCurrentView(view);
    }
}
