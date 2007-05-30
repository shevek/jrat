package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.ui.UIConstants;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;


public class JRatFrame extends JFrame implements UIConstants {

    private static int openCount = 0;

    public JRatFrame(String title, Component component) throws HeadlessException {

        this(title);

        Container container = getContentPane();

        container.setLayout(new BorderLayout());
        container.add(component, BorderLayout.CENTER);
    }


    public JRatFrame(String title) throws HeadlessException {

        super(title);

        init();
    }


    public JRatFrame() throws HeadlessException {

        super();

        init();
    }


    private void init() {
        setIcon();
        setDimension();
    }


    private void setDimension() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int width = (int) (screenSize.getWidth() * FRAME_WIDTH_PCT);
        int height = (int) (screenSize.getHeight() * FRAME_HEIGHT_PCT);
        int x = (int) ((screenSize.getWidth() / 2) - (width / 2));
        int y = (int) ((screenSize.getHeight() / 2) - (height / 2));

        x += (openCount * 10);
        y += (openCount * 10);

        setLocation(x, y);
        setSize(width, height);

        openCount++;

        openCount %= 10;
    }


    private void setIcon() {

        // this causes a problem with X11
        //              try {
        //                      URL url = getClass().getClassLoader().getResource(ICON);
        //                      Image image = Toolkit.getDefaultToolkit().getImage(url);
        //                      setIconImage(image);
        //              } catch (Throwable e) {
        //              }
    }
}
