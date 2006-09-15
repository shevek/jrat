package org.shiftone.jrat.ui;



import org.shiftone.jrat.util.SavedProperties;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;

import java.util.Properties;


/**
 * Class UserSettings
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.21 $
 */
public class UserSettings {

    private static final Logger      LOG                     = Logger.getLogger(UserSettings.class);
    public static final Properties   PROPS                   = SavedProperties.USER_PROPERTIES;
    public static final String       PROP_LAST_OPENED_FILE   = "lastOpenedOutputFile";
    public static final String       PROP_LAST_INJECTED_FILE = "lastInjectedFile";
    public static final String       PROP_LAST_INJECTED_DIR  = "lastInjectedDir";
    public static final UserSettings INSTANCE                = new UserSettings();

    private UserSettings() {}


    public File getLastOpenedOutputFile() {
        return getFileProperty(PROP_LAST_OPENED_FILE);
    }


    public void setLastOpenedOutputFile(File fileName) {
        setFileProperty(PROP_LAST_OPENED_FILE, fileName);
    }


    public File getLastInjectedFile() {
        return getFileProperty(PROP_LAST_INJECTED_FILE);
    }


    public void setLastInjectedFile(File fileName) {
        setFileProperty(PROP_LAST_INJECTED_FILE, fileName);
    }


    public File getLastInjectedDir() {
        return getFileProperty(PROP_LAST_INJECTED_DIR);
    }


    public void setLastInjectedDir(File fileName) {
        setFileProperty(PROP_LAST_INJECTED_DIR, fileName);
    }


    public File getFileProperty(String property) {
        return toFile(PROPS.getProperty(property));
    }


    public void setFileProperty(String property, File file) {
        PROPS.setProperty(property, file.getAbsolutePath());
    }


    private File toFile(String fileName) {

        return (fileName == null)
               ? null
               : new File(fileName);
    }
}
