package org.shiftone.jrat.core;



import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.core.shutdown.ShutdownRegistry;
import org.shiftone.jrat.core.jmx.benchmark.Benchmark;
import org.shiftone.jrat.core.output.FileOutputFactory;
import org.shiftone.jrat.core.output.FileOutputRegistry;
import org.shiftone.jrat.core.jmx.JmxRegistry;
import org.shiftone.jrat.core.jmx.JmxRegistryFactory;
import org.shiftone.jrat.core.jmx.info.JRatInfo;
import org.shiftone.jrat.core.command.CommandletRegistry;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggingManager;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.14 $
 */
public class ServiceFactory {

    private static final Logger   LOG = Logger.getLogger(ServiceFactory.class);
    private static ServiceFactory singleton;

    public ServiceFactory() {
        LOG.info("new");
    }


    public static synchronized ServiceFactory getInstance() {

        if (singleton == null)
        {
            singleton = new ServiceFactory();
        }

        return singleton;
    }


    private CommandletRegistry commandletRegistry = new CommandletRegistry();
    public synchronized CommandletRegistry getCommandletRegistry() {
        // perhaps you were expecting something more magical..
        return commandletRegistry;
    }

    // --------------------------------------------------------------------
    private JmxRegistry jmxRegistry;

    public synchronized JmxRegistry getJmxRegistry() {

        if (jmxRegistry == null)
        {
            jmxRegistry = JmxRegistryFactory.createJmxRegistry();

            jmxRegistry.registerMBean(new JRatInfo(), null);
            jmxRegistry.registerMBean(new LoggingManager(), null);
            jmxRegistry.registerMBean(new Benchmark(), null);
        }

        return jmxRegistry;
    }


    // --------------------------------------------------------------------
    private FileOutputRegistry fileOutputRegistry;

    public synchronized FileOutputRegistry getFileOutputRegistry() {

        if (fileOutputRegistry == null)
        {
            fileOutputRegistry = new FileOutputRegistry();

            getJmxRegistry().registerMBean(fileOutputRegistry, null);
            getShutdownRegistry().registerShutdownListener(fileOutputRegistry);
        }

        return fileOutputRegistry;
    }


    // --------------------------------------------------------------------
    private FileOutputFactory fileOutputFactory;

    public synchronized FileOutputFactory getFileOutputFactory() {

        if (fileOutputFactory == null)
        {
            fileOutputFactory = new FileOutputFactory(getFileOutputRegistry());
        }

        return fileOutputFactory;
    }


    // --------------------------------------------------------------------
    private ShutdownRegistry shutdownRegistry;

    public synchronized ShutdownRegistry getShutdownRegistry() {

        if (shutdownRegistry == null)
        {
            shutdownRegistry = new ShutdownRegistry();

            getJmxRegistry().registerMBean(shutdownRegistry, null);
        }

        return shutdownRegistry;
    }


    // --------------------------------------------------------------------
    private Transformer transformer;

    public synchronized Transformer getTransformer() {

        if (transformer == null)
        {
            transformer = new Transformer();

            getJmxRegistry().registerMBean(transformer, null);
            getShutdownRegistry().registerShutdownListener(transformer);
        }

        return transformer;
    }
}
