package org.shiftone.jrat.provider.tree;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface TreeMethodHandlerFactoryMBean {

    long getMethodHandlerCount();


    void writeOutputFile();


    void writeOutputFile(String fileName);
}
