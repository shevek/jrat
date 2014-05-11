package org.shiftone.jrat.desktop;

import javax.swing.JOptionPane;
import org.shiftone.jrat.util.io.ResourceUtil;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DesktopDemo {

    public static void main(String[] args) {

        final DesktopFrame frame = new DesktopFrame();
        frame.setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                frame.open("demo.jrat", ResourceUtil.loadResourceAsStream("demo.jrat"));
            }
        }).start();

        JOptionPane.showMessageDialog(
                frame,
                "The JRat Desktop is running in demo mode.\n"
                + "A sample data set is being downloaded and loaded.",
                "Running Demo Mode",
                JOptionPane.INFORMATION_MESSAGE);

    }

}
