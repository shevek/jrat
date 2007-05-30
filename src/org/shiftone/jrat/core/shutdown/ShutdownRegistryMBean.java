package org.shiftone.jrat.core.shutdown;


/**
 * @author Jeff Drost
 */
public interface ShutdownRegistryMBean {

    int getShutdownListenerCount();


    String getShutdownListenersHtml();


    void forceShutdownNow();
}
