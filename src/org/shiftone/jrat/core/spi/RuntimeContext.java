package org.shiftone.jrat.core.spi;


import org.shiftone.jrat.core.shutdown.ShutdownListener;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.Serializable;


/**
 * @author Jeff Drost
 */
public interface RuntimeContext {

    void writeSerializable(String fileName, Serializable serializable);
    

    OutputStream createOutputStream(String fileName);


    PrintWriter createPrintWriter(String fileName);


    Writer createWriter(String fileName);


    void registerMBean(Object mbean);


    void registerMBean(Object mbean, String objectNameText);


    void register(Commandlet commandlet);


    long uniqNumber();


    void registerShutdownListener(ShutdownListener listener);


    long getStartTimeMs();
}
