package org.shiftone.jrat.desktop.util;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.awt.*;
import java.io.*;

/**
 * This class will be serialized to the user's home directory in order to persist UI state.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Preferences implements Serializable {

    private static final Logger LOG = Logger.getLogger(Preferences.class);
    private static final long serialVersionUID = 1;
    private transient File file;
    private int runCount;
    private long lastRunTime;
    private Rectangle windowBounds;
    private File lastOpenedFile;

    /**
     * todo - better error handling
     */
    public static Preferences load() {

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
}
