package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.core.spi.ViewBuilder;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.desktop.util.Preferences;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class OpenAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(OpenAction.class);
    private final DesktopFrame desktopFrame;
    private final Preferences preferences;


    public OpenAction(DesktopFrame desktopFrame) {
        super("Open");

        this.desktopFrame = desktopFrame;
        this.preferences = desktopFrame.getPreferences();

        putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
    }

    private File getCurrentDirectory() {
        File file = preferences.getLastOpenedFile();
        return (file != null) ? getParent(file) : new File("");
    }

    private File getSelectedFile() {
        File file = preferences.getLastOpenedFile();
        return (file != null && file.exists()) ? file : null;
    }

    private File getParent(File file) {
        File parent = file.getParentFile();
        if (parent.exists()) {
            return parent;
        } else {
            return getParent(parent);
        }
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open JRat Output File");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(getCurrentDirectory());
        chooser.setSelectedFile(getSelectedFile());
        chooser.addChoosableFileFilter(JRatFileFilter.INSTANCE);
        //chooser.addChoosableFileFilter(SessionFileFilter.INSTANCE);

        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(desktopFrame)) {
            openFiles(chooser.getSelectedFiles());
        }
    }


    private void openFiles(File[] files) {
        for (int i = 0; i < files.length; i++) {
            openFile(files[i]);
        }
    }

    private void openFile(File file) {
        preferences.setLastOpenedFile(file);
        new Thread(new OpenRunnable(file)).start();
    }


    private class OpenRunnable implements Runnable {
        private final File file;


        public OpenRunnable(File file) {
            this.file = file;
        }

        public void run() {

            InputStream inputStream = null;

            try {
                desktopFrame.waitCursor();
                inputStream = IOUtil.openInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                LOG.info("reading : " + file);
                ViewBuilder viewBuilder = (ViewBuilder) objectInputStream.readObject();

                LOG.info("building view");
                JComponent component = viewBuilder.buildView(file);

                desktopFrame.createView(file.getName(), component);

            } catch (Exception e) {

                LOG.error("failed to read " + file.getAbsolutePath(), e);

            } finally {

                desktopFrame.unwaitCursor();
                IOUtil.close(inputStream);

            }
        }
    }

//    private static class SessionFileFilter extends FileFilter {
//
//        static FileFilter INSTANCE = new SessionFileFilter();
//
//        public boolean accept(File f) {
//            if (f.isDirectory()) {
//                return true;
//            }
//            return (f.getName().endsWith(".session"));
//        }
//
//        public String getDescription() {
//            return "Session File (*.session)";
//        }
//    }

    private static class JRatFileFilter extends FileFilter {

        static FileFilter INSTANCE = new JRatFileFilter();

        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return (f.getName().endsWith(".jrat"));
        }

        public String getDescription() {
            return "JRat Data File (*.jrat)";
        }
    }
}
