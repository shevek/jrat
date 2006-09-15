package org.shiftone.jrat.core.jmx.info;



import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.Clock;

import java.lang.reflect.Method;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public class JRatInfo implements JRatInfoMBean {

    private static final Logger LOG = Logger.getLogger(JRatInfo.class);

    public String getBuiltBy() {
        return VersionUtil.getBuiltBy();
    }


    public String getBuiltOn() {
        return VersionUtil.getBuiltOn();
    }


    public String getVersion() {
        return VersionUtil.getVersion();
    }


    public String getClockStrategy() {
        return Clock.getStrategyText();
    }


    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }


    public long getMaxMemory() {

        try
        {
            Method maxMemory = Runtime.class.getMethod("maxMemory", new Class[]{});
            Long   result    = (Long) maxMemory.invoke(Runtime.getRuntime(), new Object[]{});

            return result.longValue();
        }
        catch (Exception e) {}

        return 0;
    }


    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }


    public int getActiveThreadCount() {
        return Thread.activeCount();
    }


    public void gc() {
        Runtime.getRuntime().gc();
    }
}
