package org.shiftone.jrat.core.config;

import org.shiftone.jrat.util.io.Dir;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Settings {
    private static final Logger LOG = Logger.getLogger(Settings.class);

    private String applicationName;

    private boolean systemPropertyTweakingEnabled = true;
    private String baseDirectory = "jrat.output";
    private String logLevel = "info";
    private boolean nanoSecondTimingEnabled;
    private int outputBufferSize = 1024 * 8;
    private boolean outputCompressionEnabled;

    //
    private boolean httpServerEnabled = true;
    private int httpPort = 9009;
    //
    private boolean jmxEnabled;
    private boolean mBeanServerCreationEnabled;
    private String mBeanServerServerUrl;
    private String mBeanServerAgentId;
    private boolean rmiRegistryCreationEnabled = true;
    private int rmiRegistryPort = 2121;
    //

    private boolean injectorDefaultExcludesEnabled;


    /**
     * It is occasionally necessary for JRat to set a system property that
     * it knows will change the behavior of an environment in a way that
     * is necessary for profiling.
     * This is only done when using the jvmti agent.
     */
    public boolean isSystemPropertyTweakingEnabled() {
        return systemPropertyTweakingEnabled;
    }

    public void setSystemPropertyTweakingEnabled(boolean systemPropertyTweakingEnabled) {
        this.systemPropertyTweakingEnabled = systemPropertyTweakingEnabled;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Dir getBaseDirectory() {
        return new Dir(baseDirectory);
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public boolean isNanoSecondTimingEnabled() {
        return nanoSecondTimingEnabled;
    }

    public void setNanoSecondTimingEnabled(boolean nanoSecondTimingEnabled) {
        this.nanoSecondTimingEnabled = nanoSecondTimingEnabled;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public boolean isOutputCompressionEnabled() {
        return outputCompressionEnabled;
    }

    public void setOutputCompressionEnabled(boolean outputCompressionEnabled) {
        this.outputCompressionEnabled = outputCompressionEnabled;
    }

    public boolean isHttpServerEnabled() {
        return httpServerEnabled;
    }

    public void setHttpServerEnabled(boolean httpServerEnabled) {
        this.httpServerEnabled = httpServerEnabled;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public boolean isJmxEnabled() {
        return jmxEnabled;
    }

    public void setJmxEnabled(boolean jmxEnabled) {
        this.jmxEnabled = jmxEnabled;
    }

    public boolean isMBeanServerCreationEnabled() {
        return mBeanServerCreationEnabled;
    }

    public void setMBeanServerCreationEnabled(boolean mBeanServerCreationEnabled) {
        this.mBeanServerCreationEnabled = mBeanServerCreationEnabled;
    }

    public String getMBeanServerServerUrl() {
        return this.mBeanServerServerUrl == null
                ? "service:jmx:rmi:///jndi/rmi://localhost:" + getRmiRegistryPort() + "/jrat"
                : this.mBeanServerServerUrl;
    }

    public void setMBeanServerServerUrl(String mBeanServerServerUrl) {
        this.mBeanServerServerUrl = mBeanServerServerUrl;
    }

    public String getMBeanServerAgentId() {
        return mBeanServerAgentId;
    }

    public void setMBeanServerAgentId(String mBeanServerAgentId) {
        this.mBeanServerAgentId = mBeanServerAgentId;
    }

    public boolean isRmiRegistryCreationEnabled() {
        return rmiRegistryCreationEnabled;
    }

    public void setRmiRegistryCreationEnabled(boolean rmiRegistryCreationEnabled) {
        this.rmiRegistryCreationEnabled = rmiRegistryCreationEnabled;
    }

    public int getRmiRegistryPort() {
        return rmiRegistryPort;
    }

    public void setRmiRegistryPort(int rmiRegistryPort) {
        this.rmiRegistryPort = rmiRegistryPort;
    }

    public boolean isInjectorDefaultExcludesEnabled() {
        return injectorDefaultExcludesEnabled;
    }

    public void setInjectorDefaultExcludesEnabled(boolean injectorDefaultExcludesEnabled) {
        this.injectorDefaultExcludesEnabled = injectorDefaultExcludesEnabled;
    }
}
