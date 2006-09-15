package org.shiftone.jrat.core.shutdown;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public interface ShutdownRegistryMBean {

    int getShutdownListenerCount();


    String getShutdownListenersHtml();


    void forceShutdownNow();
}
