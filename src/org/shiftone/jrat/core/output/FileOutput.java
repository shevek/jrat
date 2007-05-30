package org.shiftone.jrat.core.output;



/**
 * @author Jeff Drost
 *
 */
public interface FileOutput {

    public void close() throws Exception;


    public void flush() throws Exception;
}
