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
    public void testRegisterBean() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(new TestObject(), new ObjectName("shiftone.jrat:service=Test"));
    }
}
