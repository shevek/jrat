package org.shiftone.jrat.desktop.action.file;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.shiftone.jrat.desktop.DesktopPreferences;
import org.shiftone.jrat.desktop.util.Errors;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ClearPreferencesAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(ClearPreferencesAction.class);
    private final Component component;

    public ClearPreferencesAction(Component component) {
        super("Clear All Preferences");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        this.component = component;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            Preferences desktop = Preferences.userNodeForPackage(DesktopPreferences.class);
            Preferences jrat = desktop.parent();
            // leaving the shiftone node
            jrat.removeNode();
        } catch (Exception e) {
            Errors.showError(component, e, "Failed to clear preferences");
        }
    }
}
