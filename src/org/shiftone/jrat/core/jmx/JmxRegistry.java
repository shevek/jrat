package org.shiftone.jrat.core.jmx;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public interface JmxRegistry {

    boolean isReady();


    void registerMBean(Object object, String name);
}
