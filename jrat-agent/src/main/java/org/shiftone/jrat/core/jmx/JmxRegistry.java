package org.shiftone.jrat.core.jmx;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface JmxRegistry {

    boolean isReady();


    void registerMBean(Object object, String name);
}
