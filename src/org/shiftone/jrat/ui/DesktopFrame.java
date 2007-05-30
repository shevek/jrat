package org.shiftone.jrat.ui;


import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.ui.action.ExitAction;
import org.shiftone.jrat.ui.action.RemoveAllTabsAction;
import org.shiftone.jrat.ui.action.RemoveCurrentTabAction;
import org.shiftone.jrat.ui.action.ResizeAction;
import org.shiftone.jrat.ui.help.AboutAction;
import org.shiftone.jrat.ui.help.ShowDocsAction;
import org.shiftone.jrat.ui.inject.InjectDirAction;
import org.shiftone.jrat.ui.status.StatusPanel;
import org.shiftone.jrat.ui.tab.TabbedPaneViewContainer;
import org.shiftone.jrat.ui.util.JRatFrame;
import org.shiftone.jrat.ui.viewer.OpenOutputFileAction;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.swing.popup.CloseTabbedPanePopupMouseAdaptor;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.io.File;


/**
 * Class DesktopFrame
 *
 * @author Jeff Drost
 */
public class DesktopFrame extends JRatFrame implements UIConstants, ContainerListener {

    private static final Logger LOG = Logger.getLogger(DesktopFrame.class);
    private JMenuBar menuBar = new JMenuBar();
    private TabbedPaneViewContainer tabbedPane = new TabbedPaneViewContainer();
    private StatusPanel statusPanel = new StatusPanel();
    private ViewContainer viewContainer = tabbedPane;
    private JMenu fileMenu = new JMenu(MENU_FILE);
    private JMenu windowMenu = new JMenu(MENU_WINDOW);
    private JMenu injectMenu = new JMenu(MENU_INSTRUMENT);
    private JMenu helpMenu = new JMenu(MENU_HELP);
    private JMenuItem open = new JMenuItem(MENU_OPEN);
    private JMenuItem close = new JMenuItem(MENU_CLOSE);
    private JMenuItem closeAll = new JMenuItem(MENU_CLOSE_ALL);
    private JMenuItem exit = new JMenuItem(MENU_EXIT);
    private JMenuItem injectFile = new JMenuItem(MENU_INJECT_JAR);
    private JMenuItem injectDir = new JMenuItem(MENU_INJECT_DIR);
    private JMenuItem docs = new JMenuItem(MENU_DOCS);
    private JMenuItem license = new JMenuItem(MENU_LICENSE);
    private JMenuItem about = new JMenuItem(MENU_ABOUT);
    private JMenuItem resize640x480 = new JMenuItem(MENU_640X480);
    private JMenuItem resize800x600 = new JMenuItem(MENU_800x600);
    private OpenOutputFileAction outputFileAction;
    private Injector injector = new Injector();

    public DesktopFrame() {

        Container pane = getContentPane();

        pane.setLayout(new BorderLayout());
        pane.add(statusPanel, BorderLayout.SOUTH);
        pane.add(tabbedPane, BorderLayout.CENTER);
        addMenuBar();
        setTitle(UI_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void addMenuBar() {

        fileMenu.add(open);
        fileMenu.add(close);
        fileMenu.add(closeAll);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        fileMenu.setMnemonic('F');
        open.setMnemonic('O');
        exit.setMnemonic('X');

        //
        windowMenu.add(resize640x480);
        windowMenu.add(resize800x600);
        windowMenu.setMnemonic('W');

        //
        injectMenu.add(injectFile);
        injectMenu.add(injectDir);
        injectMenu.setMnemonic(KeyEvent.VK_I);

        //
        helpMenu.add(docs);
        helpMenu.add(license);
        helpMenu.add(about);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        about.setMnemonic(KeyEvent.VK_A);
        docs.setMnemonic(KeyEvent.VK_H);
        tabbedPane.addMouseListener(new CloseTabbedPanePopupMouseAdaptor(tabbedPane));

        //
        outputFileAction = new OpenOutputFileAction(this, viewContainer);

        open.addActionListener(outputFileAction);
        close.addActionListener(new RemoveCurrentTabAction(viewContainer));
        closeAll.addActionListener(new RemoveAllTabsAction(viewContainer));
        exit.addActionListener(new ExitAction());
        injectFile.addActionListener(InjectDirAction.createForFiles(injector, viewContainer));
        injectDir.addActionListener(InjectDirAction.createForDirs(injector, viewContainer));
        docs.addActionListener(new ShowDocsAction(viewContainer, ShowDocsAction.HOME_TITLE, ShowDocsAction.HOME_URL));
        license.addActionListener(new ShowDocsAction(viewContainer, ShowDocsAction.LICENSE_TITLE,
                ShowDocsAction.LICENSE_URL));
        about.addActionListener(new AboutAction(this));
        resize640x480.addActionListener(new ResizeAction(this, 640, 480));
        resize800x600.addActionListener(new ResizeAction(this, 800, 600));
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        menuBar.add(injectMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        //
        tabbedPane.addContainerListener(this);
        tabbedCountChanged(0);
    }


    public void openFile(File file) throws Exception {
        outputFileAction.openFile(file);
    }


    public void componentAdded(ContainerEvent e) {
        tabbedCountChanged(tabbedPane.getTabCount());
    }


    public void componentRemoved(ContainerEvent e) {
        tabbedCountChanged(tabbedPane.getTabCount());
    }


    private void tabbedCountChanged(int tabCount) {

        LOG.info("tabbedCountChanged  " + tabCount);

        boolean hasTabs = (tabCount > 0);

        close.setEnabled(hasTabs);
        closeAll.setEnabled(hasTabs);
    }


    public void setEnabled(boolean isEnable) {

        if (isEnable) {
            menuBar.setEnabled(true);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } else {
            menuBar.setEnabled(false);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }

        // super.setEnabled(enable);
    }
}
