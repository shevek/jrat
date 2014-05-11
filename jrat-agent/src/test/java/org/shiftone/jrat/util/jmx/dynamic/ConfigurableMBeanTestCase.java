package org.shiftone.jrat.util.jmx.dynamic;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.junit.Test;
import org.shiftone.jrat.core.jmx.info.JRatInfo;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class ConfigurableMBeanTestCase {

    private static final Logger LOG = Logger.getLogger(ConfigurableMBeanTestCase.class);

    @Test
    public void testOne() throws Exception {

        ConfigurableMBean beanAttribute = new ConfigurableMBean("this is a config mbean");
        beanAttribute.add("testString", new SimpleAttributeValue("this is a test"));
        beanAttribute.add("testLong", new SimpleAttributeValue(new Long(12345)));
        beanAttribute.add("testDouble", new SimpleAttributeValue(new Double(1.2)));
        beanAttribute.add("doIt", new RunnableOperation() {
            @Override
            public String getDescription() {
                return "do it";
            }

            @Override
            public void run() {
                LOG.info("do it");
            }
        });

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        mBeanServer.registerMBean(beanAttribute, new ObjectName("test:number=1"));
        mBeanServer.registerMBean(new JRatInfo(), new ObjectName("test:number=2"));

        //  Thread.sleep(1000 * 60 * 5);
    }

}
