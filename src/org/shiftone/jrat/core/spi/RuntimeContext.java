package org.shiftone.jrat.core.spi;



import org.shiftone.jrat.core.shutdown.ShutdownListener;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.17 $
 */
public interface RuntimeContext {

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
