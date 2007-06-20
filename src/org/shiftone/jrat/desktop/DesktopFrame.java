package org.shiftone.jrat.desktop;

import org.jdesktop.swingx.JXStatusBar;
import org.shiftone.jrat.desktop.action.file.ExitAction;
import org.shiftone.jrat.desktop.action.file.OpenAction;
import org.shiftone.jrat.desktop.action.file.CloseAction;
import org.shiftone.jrat.desktop.action.file.WindowSizeAction;
import org.shiftone.jrat.desktop.action.help.AboutAction;
import org.shiftone.jrat.desktop.action.help.DocsAction;
import org.shiftone.jrat.desktop.action.help.LicenseAction;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Dimension;

/**
 * @Author Jeff Drost
 */
public class DesktopFrame extends JFrame {

    private static final Logger LOG = Logger.getLogger(DesktopFrame.class);
    private JXStatusBar statusBar = new JXStatusBar();

    public DesktopFrame() {
        super("JRat Desktop");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) dimension.getWidth() - 150, (int) dimension.getHeight() - 150);
        // setStartPosition(StartPosition.CenterInScreen);
        // setStatusBar(statusBar);
        setLocation(50, 50);
        statusBar.add(new JLabel("test"));
        setJMenuBar(createMenuBar());

    }

    private JMenuBar createMenuBar() {
        JMenuBar toolBar = new JMenuBar();
        {
            JMenu file = new JMenu("File");
            file.setMnemonic('F');
            file.add(new OpenAction());
            file.add(new CloseAction());
            {
                JMenu resize = new JMenu("Window Size");
                resize.setMnemonic('S');
                resize.add(new WindowSizeAction(this, 640, 480));
                resize.add(new WindowSizeAction(this, 800, 600));
                file.add(resize);
            }                                    
            file.add(new ExitAction());

            toolBar.add(file);
        }

        {
            JMenu inject = new JMenu("Inject");
            inject.setMnemonic('I');
            toolBar.add(inject);
        }

        {
            JMenu help = new JMenu("Help");
            help.setMnemonic('H');
            help.add(new AboutAction());
            help.add(new DocsAction());
            help.add(new LicenseAction());
            toolBar.add(help);
        }

        return toolBar;
    }

}
