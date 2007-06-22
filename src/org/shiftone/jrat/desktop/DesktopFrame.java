package org.shiftone.jrat.desktop;

import org.jdesktop.swingx.JXStatusBar;
import org.shiftone.jrat.desktop.action.file.ExitAction;
import org.shiftone.jrat.desktop.action.file.OpenAction;
import org.shiftone.jrat.desktop.action.file.CloseAction;
import org.shiftone.jrat.desktop.action.file.WindowSizeAction;
import org.shiftone.jrat.desktop.action.file.CloseAllAction;
import org.shiftone.jrat.desktop.action.help.AboutAction;
import org.shiftone.jrat.desktop.action.help.DocsAction;
import org.shiftone.jrat.desktop.action.help.LicenseAction;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;

/**
 * @Author Jeff Drost
 */
public class DesktopFrame extends JFrame {

    private static final Logger LOG = Logger.getLogger(DesktopFrame.class);
    private JXStatusBar statusBar = new JXStatusBar();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private CloseAction closeAction = new CloseAction(tabbedPane);
    private CloseAllAction closeAllAction = new CloseAllAction(tabbedPane);
    private int waiters = 0;

    public DesktopFrame() {
        super("JRat Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) dimension.getWidth() - 150, (int) dimension.getHeight() - 150);
        // setStartPosition(StartPosition.CenterInScreen);
        // setStatusBar(statusBar);
        setLocation(50, 50);
        statusBar.add(new JLabel("test"));
        setJMenuBar(createMenuBar());

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addMouseListener(new TabMouseListener(tabbedPane));
        tabbedPane.addContainerListener(new TabChangeListener());
        checkTabs();


    }

    public void waitCursor() {
        waiters++;
        setCursor(Cursor.WAIT_CURSOR);
    }

    public void unwaitCursor() {
        waiters--;
        if (waiters == 0) {
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar toolBar = new JMenuBar();
        {
            JMenu file = new JMenu("File");
            file.setMnemonic('F');
            file.add(new OpenAction(this));
            file.add(closeAction);
            file.add(closeAllAction);
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

    public View createView(final String title, JComponent component) {
        final View view = new View();
        view.setComponent(component);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                tabbedPane.addTab(title, view);
            }
        });
        return view;
    }

    private void checkTabs() {
        boolean enableClose = tabbedPane.getTabCount() > 0;
        closeAction.setEnabled(enableClose);
        closeAllAction.setEnabled(enableClose);
    }

    private class TabChangeListener implements ContainerListener {

        public void componentAdded(ContainerEvent e) {
            checkTabs();
        }

        public void componentRemoved(ContainerEvent e) {
            checkTabs();
        }
    }
}
