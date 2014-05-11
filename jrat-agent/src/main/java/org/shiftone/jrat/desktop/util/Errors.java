package org.shiftone.jrat.desktop.util;

import java.awt.Component;
import java.util.logging.Level;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Errors {

    public static void showError(Component component, Throwable throwable, String errorMessage) {
        showDialog(component, Level.SEVERE, throwable, errorMessage);
    }

    public static void showWarning(Component component, Throwable throwable, String errorMessage) {
        showDialog(component, Level.WARNING, throwable, errorMessage);
    }

    public static void showDialog(Component component, Level level, Throwable throwable, String errorMessage) {
        JXErrorPane.showDialog(component,
                new ErrorInfo(errorMessage, errorMessage, null, null, throwable, level, null)
        );
    }
}
