package org.shiftone.jrat.desktop;

import org.shiftone.jrat.util.StringUtil;

import java.awt.*;
import java.io.File;
import java.util.prefs.Preferences;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DesktopPreferences {
    private static final Preferences PREFS = Preferences.userNodeForPackage(DesktopPreferences.class);
    private static final String RUN_COUNT = "run_count";
    private static final String TIPS_ON_START = "show_tips_on_startup";
    private static final String LAST_RUN_TIME = "last_run_time";
    private static final String LAST_OPENED_FILE = "last_opened_file";
    private static final String WINDOW_BOUNDS = "window_bounds";

    public static int getRunCount() {
        return PREFS.getInt(RUN_COUNT, 0);
    }

    public static void setRunCount(int runCount) {
        PREFS.putInt(RUN_COUNT, runCount);
    }

    public static void incrementRunCount() {
        setRunCount(getRunCount() + 1);
    }

    public static boolean isShowTipsOnStartup() {
        return PREFS.getBoolean(TIPS_ON_START, true);
    }

    public static void setShowTipsOnStartup(boolean showTipsOnStartup) {
        PREFS.putBoolean(TIPS_ON_START, showTipsOnStartup);
    }

    public static long getLastRunTime() {
        return PREFS.getLong(LAST_RUN_TIME, 0);
    }

    public static void setLastRunTime(long lastRunTime) {
        PREFS.putLong(LAST_RUN_TIME, lastRunTime);
    }

    public static Rectangle getWindowBounds() {

        String text = PREFS.get(WINDOW_BOUNDS, null);
        if (text != null) {
            String[] values = StringUtil.tokenize(text, ",", false);
            // x, y, width, height
            return new Rectangle(
                    Integer.parseInt(values[0]),
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]));
        }
        return null;
    }

    public static void setWindowBounds(Rectangle bounds) {
        PREFS.put(WINDOW_BOUNDS, bounds.x + "," + bounds.y + "," + bounds.width + "," + bounds.height);
    }

    public static File getLastOpenedFile() {
        String name = PREFS.get(LAST_OPENED_FILE, null);
        return name == null ? null : new File(name);
    }

    public static void setLastOpenedFile(File lastOpenedFile) {
        PREFS.put(LAST_OPENED_FILE, lastOpenedFile == null ? null : lastOpenedFile.getAbsolutePath());
    }
}
