package org.shiftone.jrat.desktop.action.inject;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.ui.inject.InjectRunnable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class AbstractInjectAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(AbstractInjectAction.class);
    private int fileSelectionMode;
    private String dialogTitle;
    
    public AbstractInjectAction(String string) {
        super(string);
    }


    public void actionPerformed(ActionEvent actionEvent) {

//        LOG.info("actionPerformed");
//
//        JFileChooser chooser = new JFileChooser();
//        File lastDir = null ; //= SETTINGS.getFileProperty(userPropertyName);
//
//        chooser.setDialogTitle(dialogTitle);
//        chooser.setFileSelectionMode(fileSelectionMode);
//        chooser.setMultiSelectionEnabled(true);
//
//        if (lastDir != null) {
//            chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastDir));
//            chooser.setSelectedFile(lastDir);
//        }
//
//        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
//            File[] targets = chooser.getSelectedFiles();
//
//            if (targets.length > 0) {
//                SETTINGS.setFileProperty(userPropertyName, targets[0]);
//
//                View tab = viewContainer.createView("Inject " + targets.length + " target(s)");
//
//                new Thread(new InjectRunnable(injector, targets, tab)).start();
//            }
//        }
    }
}
