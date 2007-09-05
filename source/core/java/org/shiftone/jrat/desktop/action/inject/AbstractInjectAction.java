package org.shiftone.jrat.desktop.action.inject;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.ui.inject.InjectRunnable;
import org.shiftone.jrat.desktop.DesktopFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public abstract class AbstractInjectAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(AbstractInjectAction.class);
    private final DesktopFrame desktopFrame;
    private final int fileSelectionMode;
    private final String dialogTitle;

    protected AbstractInjectAction(String string, DesktopFrame desktopFrame, int fileSelectionMode, String dialogTitle) {
        super(string);
        this.desktopFrame = desktopFrame;
        this.fileSelectionMode = fileSelectionMode;
        this.dialogTitle = dialogTitle;
    }

    protected abstract File getLastInjected();

    protected abstract void setLastInjected(File file);


    public void actionPerformed(ActionEvent actionEvent) {

        LOG.info("actionPerformed");

        JFileChooser chooser = new JFileChooser();
        File lastInjected = getLastInjected();

        chooser.setDialogTitle(dialogTitle);
        chooser.setFileSelectionMode(fileSelectionMode);
        chooser.setMultiSelectionEnabled(true);

        if (lastInjected != null) {
            chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastInjected));
            chooser.setSelectedFile(lastInjected);
        }

        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {

            File[] targets = chooser.getSelectedFiles();

            if (targets.length > 0) {

                setLastInjected(targets[0]);

                desktopFrame.createView("Injecting", new JPanel());

                //new Thread(new InjectRunnable(injector, targets, tab)).start();
            }
        }
    }
}
