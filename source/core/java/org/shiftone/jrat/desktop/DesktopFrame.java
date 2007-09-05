package org.shiftone.jrat.desktop;

import org.jdesktop.swingx.JXStatusBar;
import org.shiftone.jrat.desktop.action.file.*;
import org.shiftone.jrat.desktop.action.help.AboutAction;
import org.shiftone.jrat.desktop.action.help.DocsAction;
import org.shiftone.jrat.desktop.action.help.TipsAction;
import org.shiftone.jrat.desktop.action.inject.InjectDirectoryAction;
import org.shiftone.jrat.desktop.action.inject.InjectFileAction;
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

    private JXStatusBar statusBar = createStatusBar();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private CloseAction closeAction = new CloseAction(tabbedPane);
    private CloseAllAction closeAllAction = new CloseAllAction(tabbedPane);
    private int waiters = 0;

    public DesktopFrame() {

        super("JRat Desktop");

        DesktopPreferences.setLastRunTime(System.currentTimeMillis());
        DesktopPreferences.incrementRunCount();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Rectangle windowBounds = DesktopPreferences.getWindowBounds();

        if (windowBounds == null) {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            windowBounds = new Rectangle(50, 50, (int) dimension.getWidth() - 150, (int) dimension.getHeight() - 150);
        }

        setBounds(windowBounds);


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
            file.add(new ClearPreferencesAction(this));

            toolBar.add(file);
        }

        {
            JMenu inject = new JMenu("Inject");
            inject.setMnemonic('I');
            inject.add(new InjectDirectoryAction(this));
            inject.add(new InjectFileAction(this));

            toolBar.add(inject);
        }

        {
            JMenu help = new JMenu("Help");
            help.setMnemonic('H');
            help.add(new AboutAction(this));
            help.add(new DocsAction(this));
            help.add(new TipsAction(this));
            
            toolBar.add(help);
        }

        return toolBar;
    }

    public static JXStatusBar createStatusBar() {

        JXStatusBar statusBar = new JXStatusBar();

        statusBar.setLayout(new BorderLayout());
        statusBar.add(Memory.createMemoryButton(), BorderLayout.EAST);

        return statusBar;
    }

    public void createView(final String title, JComponent component) {
        tabbedPane.addTab(title, component);
    }

    private void checkTabs() {
        boolean enableClose = tabbedPane.getTabCount() > 0;
        closeAction.setEnabled(enableClose);
        closeAllAction.setEnabled(enableClose);
    }

    private class ComponentListener extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            DesktopPreferences.setWindowBounds(getBounds());
        }

        public void componentMoved(ComponentEvent e) {
            DesktopPreferences.setWindowBounds(getBounds());
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
