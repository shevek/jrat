package org.shiftone.jrat.desktop.util;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ColumnInfo {
    private String title;
    private boolean defaultVisible;


    public ColumnInfo(String title, boolean defaultVisible) {
        this.title = title;
        this.defaultVisible = defaultVisible;
    }


    public ColumnInfo(String title) {
        this.title = title;
        this.defaultVisible = true;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDefaultVisible() {
        return defaultVisible;
    }
}
