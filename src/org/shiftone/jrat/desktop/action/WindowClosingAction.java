package org.shiftone.jrat.desktop.action;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.shiftone.jrat.desktop.util.Preferences;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

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
            String title = "Unable to Save Preferences";
            final ErrorInfo errorInfo = new ErrorInfo(title, title, null, null, e, Level.WARNING, null);
            JXErrorPane.showDialog(component, errorInfo);
        }
        System.exit(1);
    }
}
