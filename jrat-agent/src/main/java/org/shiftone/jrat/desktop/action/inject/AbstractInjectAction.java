package org.shiftone.jrat.desktop.action.inject;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

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

    @Override
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

                RunnableLogPanel logPanel = new RunnableLogPanel();

                desktopFrame.createView("Injecting", logPanel);

                logPanel.run(new InjectRunnable(targets));

            }
        }
    }

    private class InjectRunnable implements Runnable {

        private final File[] targets;
        private final Injector injector = new Injector();

        public InjectRunnable(File[] targets) {
            this.targets = targets;
        }

        private void inject() {
            for (File target : targets) {
                injector.inject(target);
            }
        }

        @Override
        public void run() {
            inject();
        }
    }
}
