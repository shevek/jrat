package org.shiftone.jrat.desktop.util;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will be serialized to the user's home directory in order to persist UI state.
 * Why not just use java.util.prefs.Preferences?  Cuz I'm lazy... and that thing ain't type safe.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Preferences implements Serializable {

    private static final Logger LOG = Logger.getLogger(Preferences.class);
    private static final long serialVersionUID = 1;
    public static Preferences instance;

    private transient File file;
    private boolean showTipsOnStartup = true;
    private int runCount;
    private long lastRunTime;
    private Rectangle windowBounds;
    private File lastOpenedFile;
    private Map summaryTableVisibility;
    private Map values;

    public static synchronized Value value(String key) {
        if (instance == null) {
            instance = load();
        }
        return instance.getValue(key);
    }

    private static Preferences load() {

        Preferences preferences;
        String home = System.getProperty("user.home");
        File file = new File(home + File.separator + ".jrat-preferences.ser");
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;

        try {

            LOG.info("loading " + file.getAbsolutePath());

            inputStream = IOUtil.openInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);

            preferences = (Preferences) objectInputStream.readObject();

        } catch (Exception e) {

            LOG.info("starting with new preferences", e);

            // if this fails, the file doesn't exist, which is fine
            preferences = new Preferences();


        } finally {

            IOUtil.close(inputStream);

        }

        preferences.file = file;

        return preferences;
    }

    public Value getValue(String key) {
        if (values == null) {
            values = new HashMap();
        }
        return new Value(key);
    }

    public void save() throws Exception {

        OutputStream outputStream;
        ObjectOutputStream objectOutputStream = null;

        try {

            LOG.info("saving...");
            outputStream = IOUtil.openOutputStream(file, IOUtil.DEFAULT_BUFFER_SIZE);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            LOG.info("done");

        } finally {

            IOUtil.close(objectOutputStream);

        }
    }

    public int getRunCount() {
        return runCount;
    }

    public void incrementRunCount() {
        this.runCount++;
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

    public boolean isShowTipsOnStartup() {
        return showTipsOnStartup;
    }

    public void setShowTipsOnStartup(boolean showTipsOnStartup) {
        this.showTipsOnStartup = showTipsOnStartup;
    }

    public Map getSummaryTableVisibility() {
        if (summaryTableVisibility == null) {
            summaryTableVisibility = new HashMap();
        }
        return summaryTableVisibility;
    }




    public class Value implements Serializable {
        private static final long serialVersionUID = 1;
        private final String key;

        private Value(String key) {
            this.key = key;
        }

        public Serializable get() {
            return (Serializable)values.get(key);
        }
        public void set(Serializable value) {
            values.put(key, value);
        }
    }
}
