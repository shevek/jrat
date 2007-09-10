package org.shiftone.jrat.desktop;

import org.shiftone.jrat.desktop.util.Tips;
import org.shiftone.jrat.util.log.LoggerFactory;
import org.shiftone.jrat.util.io.ResourceUtil;

import java.net.URL;
import java.io.InputStream;

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

        if (demo) {

            InputStream inputStream = ResourceUtil.loadResourceAsStream("demo.jrat");
            frame.open("demo.jrat", inputStream);
        }
    }
}
