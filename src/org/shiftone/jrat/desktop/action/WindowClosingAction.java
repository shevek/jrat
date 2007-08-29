package org.shiftone.jrat.desktop.action;

import org.shiftone.jrat.desktop.util.Errors;
import org.shiftone.jrat.desktop.util.Preferences;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class WindowClosingAction extends WindowAdapter {

    private final Component component;
    private final Preferences preferences;

    public WindowClosingAction(Component component, Preferences preferences) {
        this.component = component;
        this.preferences = preferences;
    }

    public void windowClosing(WindowEvent event) {
        try {
            preferences.save();
        } catch (final Exception e) {
            Errors.showWarning(component, e, "Unable to Save Preferences");
        }
        System.exit(1);
    }
}
