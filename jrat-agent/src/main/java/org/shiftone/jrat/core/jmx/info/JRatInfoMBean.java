package org.shiftone.jrat.core.jmx.info;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface JRatInfoMBean {

    String getBuiltBy();


    String getBuiltOn();


    String getVersion();


    long getTotalMemory();


    long getMaxMemory();


    long getFreeMemory();


    int getActiveThreadCount();


    void gc();
}
