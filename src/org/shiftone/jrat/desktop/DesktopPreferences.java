package org.shiftone.jrat.desktop;

import java.io.Serializable;
import java.io.File;
import java.awt.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class DesktopPreferences implements Serializable {

    private static final long serialVersionUID = 1;
    private boolean showTipsOnStartup = true;
    private int runCount;
    private long lastRunTime;
    private Rectangle windowBounds;
    private File lastOpenedFile;


    public boolean isShowTipsOnStartup() {
        return showTipsOnStartup;
    }

    public void setShowTipsOnStartup(boolean showTipsOnStartup) {
        this.showTipsOnStartup = showTipsOnStartup;
    }

    public int getRunCount() {
        return runCount;
    }

    public void setRunCount(int runCount) {
        this.runCount = runCount;
    }

    public long getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(long lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public Rectangle getWindowBounds() {
        return windowBounds;
    }

    public void setWindowBounds(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
    }

    public File getLastOpenedFile() {
        return lastOpenedFile;
    }

    public void setLastOpenedFile(File lastOpenedFile) {
        this.lastOpenedFile = lastOpenedFile;
    }
}
