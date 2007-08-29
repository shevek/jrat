package org.shiftone.jrat.desktop.util;

import org.jdesktop.swingx.tips.TipLoader;
import org.jdesktop.swingx.tips.TipOfTheDayModel;
import org.jdesktop.swingx.JXTipOfTheDay;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.desktop.Main;

import java.util.Properties;
import java.util.prefs.*;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class Tips {

    private static final TipOfTheDayModel MODEL = TipLoader.load(
            ResourceUtil.getResourceAsProperties(
                    "org/shiftone/jrat/desktop/tips.properties"
            )
    );

    private static final JXTipOfTheDay TOTD = new JXTipOfTheDay(MODEL);
    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(Tips.class);

    public static void show(Component parent, boolean force) {
        TOTD.showDialog(parent, PREFERENCES, force);            
    }




    //TipLoader.load()
}
