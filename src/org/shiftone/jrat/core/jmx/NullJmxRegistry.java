package org.shiftone.jrat.core.jmx;



import org.shiftone.jrat.util.log.Logger;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public class NullJmxRegistry implements JmxRegistry {

    private static final Logger     LOG      = Logger.getLogger(NullJmxRegistry.class);
    public static final JmxRegistry INSTANCE = new NullJmxRegistry();

    private NullJmxRegistry() {}


    public boolean isReady() {
        return false;
    }


    public void registerMBean(Object object, String name) {
        LOG.debug("registerMBean() doing nothing - jmx disabled");
    }
}
