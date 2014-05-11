package org.shiftone.jrat.desktop.action.inject;

import java.io.File;
import javax.swing.JFileChooser;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.desktop.DesktopPreferences;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class InjectFileAction extends AbstractInjectAction {

    public InjectFileAction(DesktopFrame desktopFrame) {
        super("Inject File",
                desktopFrame,
                JFileChooser.FILES_ONLY,
                "Select File to Inject");
    }

    @Override
    protected void setLastInjected(File file) {
        DesktopPreferences.setLastInjectedFile(file);
    }

    @Override
    protected File getLastInjected() {
        return DesktopPreferences.getLastInjectedFile();
    }
}
