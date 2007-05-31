package org.shiftone.jrat.ui.inject;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.ui.UserSettings;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 * Class InjectJarAction
 *
 * @author Jeff Drost
 */
public class InjectDirAction implements ActionListener, UIConstants {

    private static final Logger LOG = Logger.getLogger(InjectDirAction.class);
    private ViewContainer viewContainer;
    private Injector injector;
    private String dialogTitle;
    private String userPropertyName;
    private int fileSelectionMode;

    public static InjectDirAction createForDirs(Injector injector, ViewContainer viewContainer) {

        InjectDirAction action = new InjectDirAction();

        action.injector = injector;
        action.viewContainer = viewContainer;
        action.dialogTitle = CHOOSE_INJECT_DIR_TITLE;
        action.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY;
        action.userPropertyName = UserSettings.PROP_LAST_INJECTED_DIR;

        return action;
    }


    public static InjectDirAction createForFiles(Injector injector, ViewContainer viewContainer) {

        InjectDirAction action = new InjectDirAction();

        action.injector = injector;
        action.viewContainer = viewContainer;
        action.dialogTitle = CHOOSE_INJECT_JAR_TITLE;
        action.fileSelectionMode = JFileChooser.FILES_ONLY;
        action.userPropertyName = UserSettings.PROP_LAST_INJECTED_FILE;

        return action;
    }


    private InjectDirAction() {
    }


    public void actionPerformed(ActionEvent e) {

        LOG.info("actionPerformed");

        JFileChooser chooser = new JFileChooser();
        File lastDir = SETTINGS.getFileProperty(userPropertyName);

        chooser.setDialogTitle(dialogTitle);
        chooser.setFileSelectionMode(fileSelectionMode);
        chooser.setMultiSelectionEnabled(true);

        if (lastDir != null) {
            chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastDir));
            chooser.setSelectedFile(lastDir);
        }

        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
            File[] targets = chooser.getSelectedFiles();

            if (targets.length > 0) {
                SETTINGS.setFileProperty(userPropertyName, targets[0]);

                View tab = viewContainer.createView("Inject " + targets.length + " target(s)");

                new Thread(new InjectRunnable(injector, targets, tab)).start();
            }
        }
    }
}
