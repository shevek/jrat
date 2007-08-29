package org.shiftone.jrat.desktop;

import org.shiftone.jrat.desktop.util.Tips;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Main {
    public static void main(String[] args) {
        DesktopFrame frame = new DesktopFrame();
        frame.setVisible(true);
        Tips.show(frame, false);
    }
}
