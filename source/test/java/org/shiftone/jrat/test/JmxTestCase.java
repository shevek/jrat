package org.shiftone.jrat.test;


import junit.framework.TestCase;
import org.shiftone.jrat.test.jmx.Test;
import org.shiftone.jrat.util.log.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 * @author Jeff Drost
 */
public class JmxTestCase extends TestCase {
    private static final Logger LOG = Logger.getLogger(JmxTestCase.class);

    public void testOne() throws Exception {

        //System.setProperty(JmxProperties.JMX_INITIAL_BUILDER, MX4JMBeanServerBuilder.class.getName());

        MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
        ArrayList arrayList = MBeanServerFactory.findMBeanServer(null);
        LOG.info("arrayList = " + arrayList);

        LocateRegistry.createRegistry(9999);

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jrat");
        JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
        connectorServer.start();


        mBeanServer.registerMBean(new Test(), new ObjectName("shiftone.jrat:service=Test"));

        Thread.sleep(1000 * 60 * 10);
    }
}
