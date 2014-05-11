package org.shiftone.jrat.desktop.action.inject;

import java.io.File;
import javax.swing.JFileChooser;
import org.shiftone.jrat.desktop.DesktopFrame;
import org.shiftone.jrat.desktop.DesktopPreferences;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class InjectDirectoryAction extends AbstractInjectAction {

    public InjectDirectoryAction(DesktopFrame desktopFrame) {
        super("Inject Directory",
                desktopFrame,
                JFileChooser.DIRECTORIES_ONLY,
                "Select Directory to Inject");
    }

    @Override
    protected void setLastInjected(File file) {
        DesktopPreferences.setLastInjectedDir(file);
    }

    @Override
    protected File getLastInjected() {
        return DesktopPreferences.getLastInjectedDir();
    }

}
