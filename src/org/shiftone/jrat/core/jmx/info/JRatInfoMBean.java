package org.shiftone.jrat.core.jmx.info;



/**
 * @author Jeff Drost
 *
 */
public interface JRatInfoMBean {

    String getBuiltBy();


    String getBuiltOn();


    String getVersion();


    String getClockStrategy();


    long getTotalMemory();


    long getMaxMemory();


    long getFreeMemory();


    int getActiveThreadCount();


    void gc();
}
