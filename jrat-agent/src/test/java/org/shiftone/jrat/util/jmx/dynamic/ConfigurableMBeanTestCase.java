package org.shiftone.jrat.util.jmx.dynamic;

import junit.framework.TestCase; 
import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.core.jmx.info.JRatInfo;
import org.shiftone.jrat.util.log.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Jeff Drost
 */
public class ConfigurableMBeanTestCase extends TestCase {
    private static final Logger LOG = Logger.getLogger(ConfigurableMBeanTestCase.class);

    public static void testOne() throws Exception {

        ConfigurableMBean beanAttribute = new ConfigurableMBean("this is a config mbean");
        beanAttribute.add("testString", new SimpleAttributeValue("this is a test"));
        beanAttribute.add("testLong", new SimpleAttributeValue(new Long(12345)));
        beanAttribute.add("testDouble", new SimpleAttributeValue(new Double(1.2)));
        beanAttribute.add("doIt", new RunnableOperation() {
            public String getDescription() {
                return "do it";
            }

            public void run() {
                LOG.info("do it");
            }
        });

        MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();

        mBeanServer.registerMBean(beanAttribute, new ObjectName("test:number=1"));
        mBeanServer.registerMBean(new JRatInfo(), new ObjectName("test:number=2"));

        int port = Environment.getSettings().getRmiRegistryPort();

        Registry registry = LocateRegistry.createRegistry(port);
        String urlText = Environment.getSettings().getMBeanServerServerUrl();

        if (urlText != null) {

            // create a URL
            JMXServiceURL url = new JMXServiceURL(urlText);

            LOG.info("Binding JMXConnectorServer to RMI jmx.");
            JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);

            LOG.info("Starting JMXConnectorServer.");
            connectorServer.start();

        }

      //  Thread.sleep(1000 * 60 * 5);
    }

}
