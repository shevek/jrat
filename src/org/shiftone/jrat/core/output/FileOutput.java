package org.shiftone.jrat.core.output;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public interface FileOutput {

    public void close() throws Exception;


    public void flush() throws Exception;
}
