package org.shiftone.jrat.ui;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;

import javax.swing.*;
import java.io.File;


/**
 * Class Desktop
 *
 * @author Jeff Drost
 */
public class Desktop {

    private static final Logger LOG = Logger.getLogger(Desktop.class);
    private DesktopFrame viewerFrame = null;

    public Desktop() {
        viewerFrame = new DesktopFrame();
    }


    public void begin() {
        LOG.debug("begin");
        viewerFrame.setVisible(true);
    }


    public static void main(String[] args) throws Exception {

        try {
            //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");

            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // UIManager.setLookAndFeel("javax.swing.plaf.basic.BasicLookAndFeel");
            // javax.swing.plaf.basic.BasicLookAndFeel
        }
        catch (Exception e) {
            LOG.warn("unable to load look and feel");
        }

        //new SplashWindow();
        LoggerFactory.enableThreadBasedLogging();

        Desktop viewer = new Desktop();

        viewer.begin();

        for (int i = 0; i < args.length; i++) {
            viewer.viewerFrame.openFile(new File(args[i]));
        }
    }
}
