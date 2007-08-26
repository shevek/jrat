package org.shiftone.jrat.desktop.util;

import javax.swing.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Icons {
    private static ClassLoader LOADER = Icons.class.getClassLoader();

    public static Icon getIcon(String path) {
        return new ImageIcon(LOADER.getResource("icons/" + path));
    }
}
