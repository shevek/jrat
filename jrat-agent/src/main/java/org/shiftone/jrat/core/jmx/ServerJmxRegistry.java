package org.shiftone.jrat.core.jmx;


import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.util.log.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ServerJmxRegistry implements JmxRegistry {

    private static final Logger LOG = Logger.getLogger(ServerJmxRegistry.class);
    private MBeanServer mBeanServer;
    private String agentId = Environment.getSettings().getMBeanServerAgentId();

    // see com.sun.jmx.defaults.JmxProperties
    public static final String JMX_INITIAL_BUILDER = "javax.management.builder.initial";

    public ServerJmxRegistry(boolean create) throws Exception {

        if (create) {
            this.mBeanServer = createMBeanServer();
        }
    }


    private static MBeanServer createMBeanServer() throws Exception {

        if (Environment.getSettings().isRmiRegistryCreationEnabled()) {

            int port = Environment.getSettings().getRmiRegistryPort();

            LOG.info("Creating local RMI jmx on port " + port + ".");

            Registry registry = LocateRegistry.createRegistry(port);
        }

        LOG.info("Creating MBeanServer (MBeanServerFactory will refer to property '" + JMX_INITIAL_BUILDER + "').");

        MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
        String urlText = Environment.getSettings().getMBeanServerServerUrl();

        if (urlText != null) {

            // column a URL
            JMXServiceURL url = new JMXServiceURL(urlText);

            LOG.info("Binding JMXConnectorServer to RMI jmx.");

            JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null,
                    mBeanServer);

            LOG.info("Starting JMXConnectorServer.");
            connectorServer.start();
        }

        return mBeanServer;
    }


    protected synchronized MBeanServer getMBeanServer() {

        if (mBeanServer == null) {
            ArrayList servers = MBeanServerFactory.findMBeanServer(agentId);

            if (servers.size() == 0) {
                LOG.debug("No MBeanServers were found.");

                return null;
            } else if (servers.size() > 1) {
                LOG.warn("More than one MBeanServers (" + servers.size() + ") was found with agentId='" + agentId
                        + "'.  Returning first.");
            }

            mBeanServer = (MBeanServer) servers.get(0);
        }

        return mBeanServer;
    }


    public boolean isReady() {
        return (getMBeanServer() != null);
    }


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
            Hashtable properties = objectName.getKeyPropertyList();
            int index = 0;

            // this will loop until an avalible objectName is found
            while (server.isRegistered(objectName)) {
                properties.put("index", String.valueOf(++index));

                objectName = new ObjectName(domain, properties);
            }

            server.registerMBean(object, objectName);
        }
        catch (Exception e) {
            LOG.warn("MBean registration failed", e);
        }
    }
}
