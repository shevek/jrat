package org.shiftone.jrat.util.io.nop;



import java.io.IOException;
import java.io.OutputStream;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.5 $
 */
public class NullOutputStream extends OutputStream {

    public static final OutputStream INSTANCE = new NullOutputStream();

    private NullOutputStream() {}


    public void write(int b) throws IOException {}


    public void close() throws IOException {}


    public void flush() throws IOException {}


    public void write(byte[] b, int off, int len) throws IOException {}


    public void write(byte[] b) throws IOException {}
}
