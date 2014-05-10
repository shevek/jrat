package org.shiftone.jrat.core.jmx.info;

import java.lang.reflect.Method;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class JRatInfo implements JRatInfoMBean {

    private static final Logger LOG = Logger.getLogger(JRatInfo.class);

    @Override
    public String getBuiltBy() {
        return VersionUtil.getBuiltBy();
    }

    @Override
    public String getBuiltOn() {
        return VersionUtil.getBuiltOn();
    }

    @Override
    public String getVersion() {
        return VersionUtil.getVersion();
    }

    @Override
    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    @Override
    public long getMaxMemory() {

        try {
            Method maxMemory = Runtime.class.getMethod("maxMemory", new Class[]{});
            Long result = (Long) maxMemory.invoke(Runtime.getRuntime(), new Object[]{});

            return result.longValue();
        } catch (Exception e) {
        }

        return 0;
    }

    @Override
    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    @Override
    public int getActiveThreadCount() {
        return Thread.activeCount();
    }

    @Override
    public void gc() {
        Runtime.getRuntime().gc();
    }
}
