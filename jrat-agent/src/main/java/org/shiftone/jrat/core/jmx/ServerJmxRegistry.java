package org.shiftone.jrat.core.jmx;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ServerJmxRegistry implements JmxRegistry {

    private static final Logger LOG = Logger.getLogger(ServerJmxRegistry.class);
    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    // see com.sun.jmx.defaults.JmxProperties
    public static final String JMX_INITIAL_BUILDER = "javax.management.builder.initial";

    protected MBeanServer getMBeanServer() {
        return mBeanServer;
    }

    @Override
    public boolean isReady() {
        return (getMBeanServer() != null);
    }

    @Override
    public void registerMBean(Object object, String objectNameText) {
        LOG.debug("registerMBean...");
        MBeanServer server = getMBeanServer();
        if (server == null) {
            LOG.info("MBeanServer is not available");
            return;
        }

        if (objectNameText == null) {
            objectNameText = "shiftone.jrat:service=" + object.getClass().getName();
        }

        try {
            LOG.info("registerMBean " + object + " " + objectNameText);

            ObjectName objectName = new ObjectName(objectNameText);
            String domain = objectName.getDomain();
            Hashtable<String, String> properties = objectName.getKeyPropertyList();
            int index = 0;

            // this will loop until an avalible objectName is found
            while (server.isRegistered(objectName)) {
                properties.put("index", String.valueOf(++index));
                objectName = new ObjectName(domain, properties);
            }

            server.registerMBean(object, objectName);
        } catch (Exception e) {
            LOG.warn("MBean registration failed", e);
        }
    }
}
