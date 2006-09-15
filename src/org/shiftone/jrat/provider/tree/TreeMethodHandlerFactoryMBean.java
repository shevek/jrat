package org.shiftone.jrat.provider.tree;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.6 $
 */
public interface TreeMethodHandlerFactoryMBean {

    long getMethodHandlerCount();


    void writeOutputFile();


    void writeOutputFile(String fileName);
}
