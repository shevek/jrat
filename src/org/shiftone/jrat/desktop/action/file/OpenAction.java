package org.shiftone.jrat.desktop.action.file;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.core.output.OutputDirectory;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.beans.PropertyVetoException;


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
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        chooser.addChoosableFileFilter(SnapshotFileFilter.INSTANCE);
        chooser.addChoosableFileFilter(SessionFileFilter.INSTANCE);

        chooser.showOpenDialog(desktopFrame);
        LOG.info("actionPerformed " + e);

        desktopFrame.createView("This is a test", new JButton());
    }

    private static class SessionFileFilter extends FileFilter {
        static FileFilter INSTANCE = new SessionFileFilter();
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return (f.getName().endsWith(".session"));
        }

        public String getDescription() {
            return "Session File (*.session)";
        }
    }

    private static class SnapshotFileFilter extends FileFilter {
        static FileFilter INSTANCE = new SnapshotFileFilter();
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return (f.getName().endsWith(".snapshot"));
        }

        public String getDescription() {
            return "Snapshot File (*.snapshot)";
        }
    }
}
