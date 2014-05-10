package org.shiftone.jrat.core.output;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface FileOutputRegistryMBean {

    int getRegisteredFileOutputCount();


    String getRegisteredFileOutputsHtml();


    void closeFileOutputs();


    void flushFileOutputs();
}
