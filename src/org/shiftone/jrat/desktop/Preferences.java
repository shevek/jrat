package org.shiftone.jrat.desktop;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.TimerTask;
import java.util.Queue;

/**
 * @Author Jeff Drost
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
        preferences.install();

        return preferences;
    }

    public void install() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                save();
            }
        }));
    }


    public void save() {

        OutputStream outputStream;
        ObjectOutputStream objectOutputStream = null;

        try {

            LOG.info("saving...");
            outputStream = IOUtil.openOutputStream(file, IOUtil.DEFAULT_BUFFER_SIZE);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            LOG.info("done");

        } catch (Exception e) {

            LOG.warn("unable to save preferences", e);

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
