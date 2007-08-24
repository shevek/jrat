package org.shiftone.jrat.util;


import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.*;
import java.util.Map;
import java.util.Properties;


/**
 * Class SavedProperties extends Properties and does write throughs to a
 * designated file to persist property values. This is not a relable way to
 * store anything critical. This class silently failes if there is an error
 * reading or writing the file.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SavedProperties extends Properties {

    private static final Logger LOG = Logger.getLogger(SavedProperties.class);
    private static final String VERSION = StringUtil.revision("$Revision: 1.21 $");
    private static final String DEFAULT_HEADER = "ShiftOne JRat SavedProperties " + VERSION;
    public static final Properties USER_PROPERTIES = new SavedProperties(getStoreFile());

    //
    private String header = DEFAULT_HEADER;
    private File storeFile = null;

    public SavedProperties(String storeFileName) {
        this(new File(storeFileName));
    }


    public SavedProperties(File storeFile) {

        Assert.assertNotNull("storeFile", storeFile);

        this.storeFile = storeFile;

        load();
    }

    private static File getStoreFile() {

        String homeDir = System.getProperty("user.home");
        if (homeDir == null) {
            homeDir = "";
        }

        return new File(new File(homeDir).getAbsolutePath()
                + File.separator
                + ".jrat-user-settings.properties");
    }

    private synchronized void store() {

        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(storeFile);

            super.store(outputStream, header);
        }
        catch (Exception e) {
            LOG.warn("unable to store properties to file : " + storeFile);
            IOUtil.close(outputStream);
        }
    }


    private synchronized void load() {

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(storeFile);

            super.load(inputStream);
        }
        catch (Exception e) {
            super.clear();
            LOG.warn("unable to load properties from file : " + storeFile);
        }
        finally {
            IOUtil.close(inputStream);
        }
    }


    public synchronized Object put(Object key, Object value) {

        Assert.assertNotNull("key", key);
        Assert.assertNotNull("value", value);

        Object o = super.put(key, value);

        store();

        return o;
    }


    public synchronized void putAll(Map t) {

        Assert.assertNotNull("map", t);
        super.putAll(t);
        store();
    }


    public synchronized Object setProperty(String key, String value) {

        Assert.assertNotNull("key", key);
        Assert.assertNotNull("value", value);

        Object o = super.setProperty(key, value);

        store();

        return o;
    }


    public synchronized Object remove(Object key) {

        Assert.assertNotNull("key", key);

        Object o = super.remove(key);

        store();

        return o;
    }
}
