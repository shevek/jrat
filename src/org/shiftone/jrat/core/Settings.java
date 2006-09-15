package org.shiftone.jrat.core;



import org.shiftone.jrat.inject.bytecode.asm.AsmInjectorStrategy;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.io.Dir;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;

import java.io.File;


/**
 * This class can be used to obtain all the configurable JRat options from
 * system properties.
 * <p>
 * I have mixed feelings about this approach...
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.19 $
 */
public class Settings {

    private static final Logger LOG                        = Logger.getLogger(Settings.class);
    private static final String DEFAULT_LOG_LEVEL          = "info";
    private static final int    DEFAULT_OUTPUT_BUFFER_SIZE = 1024 * 8;
    private static final int    DEFAULT_RMI_REGISTRY_PORT  = 2121;
    private static final String DEFAULT_BASE_DIRECTORY     = "JRatOutput";
    public static final String  CONFIGURATION_FILE         = "jrat.config.file";
    public static final String  SPRING_CONFIG_FILE         = "jrat.spring.file";
    public static final String  SPRING_BEAN_NAME           = "jrat.spring.bean";
    public static final String  HANDLER_CLASS              = "jrat.factory";

    // public static final String HANDLER_NAME = "jrat.handler";
    public static final String INJECTOR_STRATEGY                 = "jrat.injector.strategy";
    public static final String INJECTOR_DEFAULT_EXCLUDES_ENABLED = "jrat.injector.default.excludes.enabled";
    public static final String APPLICATION                       = "jrat.app";
    public static final String BASE_DIRECTORY                    = "jrat.base.dir";    // billjdap
    public static final String JMX_ENABLED                       = "jrat.jmx.enabled";
    public static final String JMX_MBEAN_SERVER_CREATE           = "jrat.jmx.create";
    public static final String JMX_MBEAN_SERVER_SERVICE_URL      = "jrat.jmx.service.url";
    public static final String JMX_MBEAN_SERVER_AGENT_ID         = "jrat.jmx.agent.id";
    public static final String RMI_REGISTRY_PORT                 = "jrat.rmi.jmx.port";
    public static final String RMI_REGISTRY_CREATE               = "jrat.rmi.jmx.create";
    public static final String OUTPUT_BUFFER_SIZE                = "jrat.output.buffers.size";
    public static final String OUTPUT_COMPRESSION                = "jrat.output.compress";
    public static final String NANOSECOND_TIMING_ENABLED         = "jrat.timing.nanoseconds";
    public static final String LOG_LEVEL                         = "jrat.log.level";
    public static final String USER_NAME                         = "user.name";
    public static final String USER_HOME                         = "user.home";
    public static final String USER_CWD                          = "user.dir";

    static
    {

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
        return getBoolean(OUTPUT_COMPRESSION, false);
    }


    public static boolean isNanoSecondTimingEnabled() {
        return getBoolean(NANOSECOND_TIMING_ENABLED, false);
    }


    public static String getLogLevel() {
        return getString(LOG_LEVEL, DEFAULT_LOG_LEVEL);
    }


    public static int getOutputBufferSize() {
        return getInteger(OUTPUT_BUFFER_SIZE, DEFAULT_OUTPUT_BUFFER_SIZE);
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
        return getBoolean(JMX_ENABLED, true);
    }


    public static boolean isMBeanServerCreationEnabled() {
        return getBoolean(JMX_MBEAN_SERVER_CREATE, false);
    }


    public static String getMBeanServerServerUrl() {
        return getString(JMX_MBEAN_SERVER_SERVICE_URL,
                         "service:jmx:rmi:///jndi/rmi://localhost:" + getRmiRegistryPort() + "/jrat");
    }


    public static String getMBeanServerAgentId() {
        return getString(JMX_MBEAN_SERVER_AGENT_ID, null);
    }


    public static boolean isRmiRegistryCreationEnabled() {
        return getBoolean(RMI_REGISTRY_CREATE, true);
    }


    public static int getRmiRegistryPort() {
        return getInteger(RMI_REGISTRY_PORT, DEFAULT_RMI_REGISTRY_PORT);
    }


    public static Dir getBaseDirectory() {
        return new Dir(getString(BASE_DIRECTORY, DEFAULT_BASE_DIRECTORY));
    }


    public static String getApplicationName() {
        return getString(APPLICATION, null);
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
        return getString(INJECTOR_STRATEGY, AsmInjectorStrategy.class.getName());
    }


    public static boolean isInjectorDefaultExcludesEnabled() {
        return getBoolean(INJECTOR_DEFAULT_EXCLUDES_ENABLED, true);
    }


    // ------------------------------------------------------------------------
    private static int getInteger(String key, int defaultValue) {

        String valueText = System.getProperty(key);
        int    value     = (valueText == null)
                           ? defaultValue
                           : Integer.parseInt(valueText);

        LOG.info("integer '" + key + "' = '" + value + "'");

        return value;
    }


    private static String getString(String key, String defaultValue) {

        String value = System.getProperty(key, defaultValue);

        if (value != null)
        {
            LOG.info("string '" + key + "' = '" + value + "'");
        }
        else
        {
            LOG.info("string '" + key + "' is null");
        }

        return value;
    }


    private static boolean getBoolean(String key, boolean defaultValue) {

        boolean result = defaultValue;
        String  value  = System.getProperty(key);

        if (value != null)
        {
            value = value.toLowerCase();

            if (("true".equals(value)) || ("yes".equals(value)) || ("t".equals(value)) || ("y".equals(value))
                    || ("on".equals(value)))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }

        LOG.info("boolean '" + key + "' = '" + result + "'");

        return result;
    }
}
