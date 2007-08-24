package org.shiftone.jrat.core.shutdown;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface ShutdownRegistryMBean {

    int getShutdownListenerCount();


    String getShutdownListenersHtml();


    void forceShutdownNow();
}
