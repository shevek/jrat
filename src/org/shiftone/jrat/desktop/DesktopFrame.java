package org.shiftone.jrat.desktop;

import org.jdesktop.swingx.JXStatusBar;
import org.shiftone.jrat.desktop.action.WindowClosingAction;
import org.shiftone.jrat.desktop.action.file.*;
import org.shiftone.jrat.desktop.action.help.AboutAction;
import org.shiftone.jrat.desktop.action.help.DocsAction;
import org.shiftone.jrat.desktop.action.help.LicenseAction;
import org.shiftone.jrat.desktop.util.Preferences;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DesktopFrame extends JFrame {

    private static final Logger LOG = Logger.getLogger(DesktopFrame.class);
    private final Preferences preferences;
    private JXStatusBar statusBar = new JXStatusBar();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private CloseAction closeAction = new CloseAction(tabbedPane);
    private CloseAllAction closeAllAction = new CloseAllAction(tabbedPane);
    private int waiters = 0;

    public DesktopFrame() {

        super("JRat Desktop");

        preferences = Preferences.getPreferences();
        preferences.setLastRunTime(System.currentTimeMillis());
        preferences.incrementRunCount();

        addWindowListener(new WindowClosingAction(this, preferences));

        Rectangle windowBounds = preferences.getWindowBounds();

        if (windowBounds == null) {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            windowBounds = new Rectangle(50, 50, (int) dimension.getWidth() - 150, (int) dimension.getHeight() - 150);
        }

        setBounds(windowBounds);

        statusBar.add(new JLabel("ShiftOne JRat"), JXStatusBar.Constraint.ResizeBehavior.FILL);


        setJMenuBar(createMenuBar());

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(tabbedPane, BorderLayout.CENTER);
        pane.add(statusBar, BorderLayout.SOUTH);

        tabbedPane.addMouseListener(new TabMouseListener(tabbedPane));
        tabbedPane.addContainerListener(new TabChangeListener());
        checkTabs();

        addComponentListener(new ComponentListener());

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
                resize.add(new WindowSizeAction(this, 1024, 768));
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
          //  help.add(new TipsAction(this));
            help.add(new LicenseAction());

            toolBar.add(help);
        }

        return toolBar;
    }


    public Preferences getPreferences() {
        return preferences;
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

    private class ComponentListener extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            preferences.setWindowBounds(getBounds());
        }

        public void componentMoved(ComponentEvent e) {
            preferences.setWindowBounds(getBounds());
        }
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
