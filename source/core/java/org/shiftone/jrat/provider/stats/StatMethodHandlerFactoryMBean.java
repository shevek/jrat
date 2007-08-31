package org.shiftone.jrat.provider.stats;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface StatMethodHandlerFactoryMBean {

    long getMethodHandlerCount();


    String dumpOutput();


    void writeOutputFile();


    void writeOutputFile(String fileName);
}
