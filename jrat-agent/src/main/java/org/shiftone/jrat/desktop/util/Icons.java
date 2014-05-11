package org.shiftone.jrat.desktop.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Icons {

    private static final ClassLoader LOADER = Icons.class.getClassLoader();

    public static Icon getIcon(String path) {
        return new ImageIcon(LOADER.getResource("org/shiftone/jrat/desktop/icons/" + path));
    }
}
