package org.shiftone.jrat.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SplashWindow extends JWindow {

    public SplashWindow() {

        JLabel l = new JLabel("JRat");    //new ImageIcon(filename));

        getContentPane().add(l, BorderLayout.CENTER);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();

        setLocation(screenSize.width / 2 - (labelSize.width / 2), screenSize.height / 2 - (labelSize.height / 2));
        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                setVisible(false);
                dispose();
            }
        });

        final int pause = 1000;
        final Runnable closerRunner = new Runnable() {

            public void run() {
                setVisible(false);
                dispose();
            }
        };
        Runnable waitRunner = new Runnable() {

            public void run() {

                try {
                    Thread.sleep(pause);
                    SwingUtilities.invokeAndWait(closerRunner);
                }
                catch (Exception e) {
                    e.printStackTrace();

                    // can catch InvocationTargetException
                    // can catch InterruptedException
                }
            }
        };

        setVisible(true);

        Thread splashThread = new Thread(waitRunner, "SplashThread");

        splashThread.start();
    }
}
