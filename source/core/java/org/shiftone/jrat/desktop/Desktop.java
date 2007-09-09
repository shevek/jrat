package org.shiftone.jrat.desktop;

import org.shiftone.jrat.desktop.util.Tips;
import org.shiftone.jrat.util.log.LoggerFactory;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Desktop {

    public static void main(String[] args) {

        boolean demo = (args.length == 1) && ("demo".equals(args[0]));

        LoggerFactory.enableThreadBasedLogging();

        DesktopFrame frame = new DesktopFrame();

        frame.setVisible(true);

        if (!demo) {
            Tips.show(frame, false);
        }

    }
}
