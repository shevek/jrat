package org.shiftone.jrat.core;


import org.shiftone.jrat.core.command.CommandletRegistry;
import org.shiftone.jrat.core.jmx.JmxRegistry;
import org.shiftone.jrat.core.output.OutputDirectory;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.core.shutdown.ShutdownRegistry;
import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.util.Properties;


/**
 * Class RuntimeContextImpl
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
class RuntimeContextImpl implements RuntimeContext {

    protected static final Logger LOG = Logger.getLogger(RuntimeContextImpl.class);
    private final AtomicLong sequence = new AtomicLong();
    private final long startTimeMs = System.currentTimeMillis();
    private final ShutdownRegistry shutdownRegistry;
    private final JmxRegistry jmxRegistry;
    private final CommandletRegistry commandletRegistry;
    private final OutputDirectory outputDirectory;
    private final Properties systemPropertiesAtStartup;
    private final MemoryMonitor memoryMonitor;

    RuntimeContextImpl() {

        LOG.info("new");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        outputDirectory = OutputDirectory.create(serviceFactory.getFileOutputFactory());
        shutdownRegistry = serviceFactory.getShutdownRegistry();
        jmxRegistry = serviceFactory.getJmxRegistry();
        commandletRegistry = serviceFactory.getCommandletRegistry();
        memoryMonitor = new MemoryMonitor(this);

        systemPropertiesAtStartup = new Properties();
        systemPropertiesAtStartup.putAll(System.getProperties());

        redirectLogStream();

    }


    public Properties getSystemPropertiesAtStartup() {
        return systemPropertiesAtStartup;
    }

    public String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }

    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown";
        }
    }

    public long getStartTimeMs() {
        return startTimeMs;
    }


    public void registerMBean(Object mbean) {
        registerMBean(mbean, null);
    }


    public void registerMBean(Object mbean, String objectNameText) {
        jmxRegistry.registerMBean(mbean, objectNameText);
    }

    public void register(Commandlet commandlet) {
        commandletRegistry.register(commandlet);
    }


    private void redirectLogStream() {

        PrintWriter printWriter;

        try {
            printWriter = outputDirectory.createPrintWriter("jrat.log");
            LoggerFactory.redirectLogging(printWriter);
            LOG.info("logfile created");
            LOG.info("Running JRat version " + VersionUtil.getVersion() + " - built on " + VersionUtil.getBuiltOn());
        }
        catch (Exception e) {
            LOG.warn("unable to redirect LOG to file", e);
        }
    }

    public PrintWriter createPrintWriter(String fileName) {
        return outputDirectory.createPrintWriter(fileName);
    }


    public OutputStream createOutputStream(String fileName) {
        return outputDirectory.createOutputStream(fileName);
    }


    public Writer createWriter(String fileName) {
        return outputDirectory.createWriter(fileName);
    }


    public synchronized long uniqNumber() {
        return sequence.incrementAndGet();
    }


    public void registerShutdownListener(ShutdownListener listener) {
        shutdownRegistry.registerShutdownListener(listener);
    }


    public void writeSerializable(String fileName, Serializable serializable) {

        OutputStream outputStream = createOutputStream(fileName);
        ObjectOutputStream objectOutputStream = null;

        try {

            // outputStream = new GZIPOutputStream(outputStream);

            objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(serializable);

            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (Exception e) {

            LOG.error("unable to write object '" + serializable + "' to file : " + fileName, e);

        } finally {
            IOUtil.close(objectOutputStream);
        }

    }

    public String toString() {
        return "RuntimeContextImpl created @ " + startTimeMs;
    }
}
