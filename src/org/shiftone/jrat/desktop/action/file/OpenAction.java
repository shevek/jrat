package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.core.spi.ViewBuilder;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.filechooser.FileFilter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;


/**
 * @Author Jeff Drost
 */
public class OpenAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(OpenAction.class);
    private DesktopFrame desktopFrame;


    public OpenAction(DesktopFrame desktopFrame) {
        super("Open");
        this.desktopFrame = desktopFrame;
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open JRat Output File");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

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
                ViewBuilder viewBuilder = (ViewBuilder)objectInputStream.readObject();

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
