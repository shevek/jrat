package org.shiftone.jrat.test;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.junit.Test;
import org.shiftone.jrat.test.jmx.TestObject;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class JmxTestCase {

    private static final Logger LOG = Logger.getLogger(JmxTestCase.class);

    @Test
    public void testCreateServer() throws Exception {

        //System.setProperty(JmxProperties.JMX_INITIAL_BUILDER, MX4JMBeanServerBuilder.class.getName());
        // MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
        // List<MBeanServer> arrayList = MBeanServerFactory.findMBeanServer(null);
        // LOG.info("arrayList = " + arrayList);

        /*
         LocateRegistry.createRegistry(9876);

         JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9876/jrat");
         JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
         connectorServer.start();
         */
        //Thread.sleep(1000 * 60 * 10);
    }

    @Test
    public void testRegisterBean() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(new TestObject(), new ObjectName("shiftone.jrat:service=Test"));
    }
}
