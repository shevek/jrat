package org.shiftone.jrat.provider.stats;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.5 $
 */
public interface StatMethodHandlerFactoryMBean {

    long getMethodHandlerCount();


    String dumpOutput();


    void writeOutputFile();


    void writeOutputFile(String fileName);
}
