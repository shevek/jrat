package org.shiftone.jrat.core;


import org.shiftone.jrat.core.boot.Environment;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.io.Dir;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;

import java.io.File;


/**
 * This class can be used to obtain all the configurable JRat options from
 * system properties.
 * <p/>
 * I have mixed feelings about this approach...
 *
 * @author Jeff Drost
 * @deprecated
 */
public class Settings {

    private static final Logger LOG = Logger.getLogger(Settings.class);

    public static final String CONFIGURATION_FILE = "jrat.config.file";
    public static final String SPRING_CONFIG_FILE = "jrat.spring.file";
    public static final String SPRING_BEAN_NAME = "jrat.spring.bean";
    public static final String HANDLER_CLASS = "jrat.factory";


    public static final String USER_NAME = "user.name";
    public static final String USER_HOME = "user.home";
    public static final String USER_CWD = "user.dir";

    private static org.shiftone.jrat.core.config.Settings settings = Environment.INSTANCE.getSettings();

    static {

        // yea - this is kinda odd.
        // there is a circular relationship between the logger
        // and settings. I want to log requests for settings,
        // but I also want to use a setting to determine the log
        // level.
        LoggerFactory.initialize();
    }

    public static String getSpringConfigFile() {
        return getString(SPRING_CONFIG_FILE, "jrat-bean-context.xml");
    }


    public static String getSpringBeanName() {
        return getString(SPRING_BEAN_NAME, "methodHandlerFactory");
    }


    public static boolean isOutputCompressionEnabled() {
        return settings.isOutputCompressionEnabled();
    }


    public static boolean isNanoSecondTimingEnabled() {
        return settings.isNanoSecondTimingEnabled();
    }


    public static String getLogLevel() {
        return settings.getLogLevel();
    }


    public static int getOutputBufferSize() {
        return settings.getOutputBufferSize();
    }


    public static String getUserName() {
        return getString(USER_NAME, "UNKNOWN");
    }


    public static File getUserPropertiesFile() {
        return new File(getUserHomeDir().getAbsolutePath() + File.separator + ".jrat.properties");
    }


    public static File getUserHomeDir() {
        return new File(getString(USER_HOME, ""));
    }


    public static boolean isJmxEnabled() {
        return settings.isJmxEnabled();
    }

    public static int isHttpPort() {
        return settings.getHttpPort();
    }

    public static boolean isHttpServerEnabled() {
        return settings.isHttpServerEnabled();
    }

    public static boolean isMBeanServerCreationEnabled() {
        return settings.isMBeanServerCreationEnabled();
    }


    public static String getMBeanServerServerUrl() {
        return settings.getMBeanServerServerUrl();
    }


    public static String getMBeanServerAgentId() {
        return settings.getMBeanServerAgentId();
    }


    public static boolean isRmiRegistryCreationEnabled() {
        return settings.isRmiRegistryCreationEnabled();
    }


    public static int getRmiRegistryPort() {
        return settings.getRmiRegistryPort();
    }


    public static Dir getBaseDirectory() {
        return settings.getBaseDirectory();
    }


    public static String getApplicationName() {
        return settings.getApplicationName();
    }


    public static File getConfigurationFile() {

        String fileName = getString(CONFIGURATION_FILE, null);

        return (fileName == null)
                ? null
                : new File(fileName);
    }


    public static String getHandlerFactoryClassName() {
        return getString(HANDLER_CLASS, SilentMethodHandler.class.getName());
    }


    public static String getInjectorStrategyClassName() {
        return settings.getInjectorStrategyClassName();
    }


    public static boolean isInjectorDefaultExcludesEnabled() {
        return settings.isInjectorDefaultExcludesEnabled();
    }


    private static String getString(String key, String defaultValue) {

        String value = System.getProperty(key, defaultValue);

        if (value != null) {
            LOG.info("string '" + key + "' = '" + value + "'");
        } else {
            LOG.info("string '" + key + "' is null");
        }

        return value;
    }


}
