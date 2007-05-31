package org.shiftone.jrat.core.jmx;


import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public class JmxRegistryFactory {

    private static final Logger LOG = Logger.getLogger(JmxRegistryFactory.class);

    public static JmxRegistry createJmxRegistry() {

        JmxRegistry registry = NullJmxRegistry.INSTANCE;
        boolean enabled = Environment.getSettings().isJmxEnabled();
        boolean create = Environment.getSettings().isMBeanServerCreationEnabled();

        if (enabled && (isJMXAvalible() == false)) {
            LOG.info("MBeanServer is not available in this environment. Disabling JMX.");

            enabled = false;
        }

        if (enabled) {
            try {
                if (create) {
                    registry = new ServerJmxRegistry(true);
                } else {
                    registry = new WaitingJmxRegistry(new ServerJmxRegistry(false));
                }
            }
            catch (Exception e) {
                LOG.error("failed to initialize JMX", e);
            }
        }

        return registry;
    }


    private static boolean isJMXAvalible() {

        try {
            Class.forName("javax.management.MBeanServer");

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
