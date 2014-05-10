package org.shiftone.jrat.core;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.InetAddress;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;
import org.shiftone.jrat.core.jmx.JmxRegistry;
import org.shiftone.jrat.core.output.OutputDirectory;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.core.shutdown.ShutdownRegistry;
import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.spi.WebActionFactory;
import org.shiftone.jrat.core.web.WebActionRegistry;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;

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
    //private final CommandletRegistry commandletRegistry;
    private final WebActionRegistry webActionRegistry;

    private final OutputDirectory outputDirectory;
    private final Properties systemPropertiesAtStartup;
    private final MemoryMonitor memoryMonitor;

    RuntimeContextImpl() {

        LOG.info("new");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        outputDirectory = OutputDirectory.create(serviceFactory.getFileOutputFactory());
        shutdownRegistry = serviceFactory.getShutdownRegistry();
        jmxRegistry = serviceFactory.getJmxRegistry();
        //commandletRegistry = serviceFactory.getCommandletRegistry();
        webActionRegistry = serviceFactory.getWebActionRegistry();
        memoryMonitor = new MemoryMonitor(this);

        systemPropertiesAtStartup = new Properties();
        systemPropertiesAtStartup.putAll(System.getProperties());

        redirectLogStream();

    }

    @Override
    public Properties getSystemPropertiesAtStartup() {
        return systemPropertiesAtStartup;
    }

    @Override
    public String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }

    @Override
    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown";
        }
    }

    @Override
    public long getStartTimeMs() {
        return startTimeMs;
    }

    @Override
    public void registerMBean(Object mbean) {
        registerMBean(mbean, null);
    }

    @Override
    public void registerMBean(Object mbean, String objectNameText) {
        jmxRegistry.registerMBean(mbean, objectNameText);
    }

    @Override
    public void register(Commandlet commandlet) {
//        commandletRegistry.register(commandlet);
    }

    private void redirectLogStream() {

        PrintWriter printWriter;

        try {
            printWriter = outputDirectory.createPrintWriter("jrat.log");
            LoggerFactory.redirectLogging(printWriter);
            LOG.info("logfile created");
            LOG.info("Running JRat version " + VersionUtil.getVersion() + " - built on " + VersionUtil.getBuiltOn());
        } catch (Exception e) {
            LOG.warn("unable to redirect LOG to file", e);
        }
    }

    @Override
    public PrintWriter createPrintWriter(String fileName) {
        return outputDirectory.createPrintWriter(fileName);
    }

    @Override
    public OutputStream createOutputStream(String fileName) {
        return outputDirectory.createOutputStream(fileName);
    }

    @Override
    public Writer createWriter(String fileName) {
        return outputDirectory.createWriter(fileName);
    }

    @Override
    public synchronized long uniqNumber() {
        return sequence.incrementAndGet();
    }

    @Override
    public void registerShutdownListener(ShutdownListener listener) {
        shutdownRegistry.registerShutdownListener(listener);
    }

    @Override
    public void registerWebActionFactory(WebActionFactory webActionFactory) {
        webActionRegistry.add(webActionFactory);
    }

    @Override
    public void writeSerializable(String fileName, Serializable serializable) {

        OutputStream outputStream = createOutputStream(fileName);
        ObjectOutputStream objectOutputStream = null;

        LOG.info("created output stream");
        try {
            outputStream = new GZIPOutputStream(outputStream);
            objectOutputStream = new ObjectOutputStream(outputStream);
            //THIS OBJECT HAS THREADS IN IT?
            objectOutputStream.writeObject(serializable);
            LOG.info("wrote object");
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (Exception e) {

            LOG.error("unable to write object '" + serializable + "' to file : " + fileName, e);

        } finally {
            IOUtil.close(objectOutputStream);
        }

    }

    @Override
    public String toString() {
        return "RuntimeContextImpl created @ " + startTimeMs;
    }
}
