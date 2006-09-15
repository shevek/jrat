package org.shiftone.jrat.core.output;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public interface FileOutputRegistryMBean {

    int getRegisteredFileOutputCount();


    String getRegisteredFileOutputsHtml();


    void closeFileOutputs();


    void flushFileOutputs();
}
