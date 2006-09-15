package org.shiftone.jrat.test;



import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.OutputStream;


/**
 * This class proxies to a OutputStream but also logs all the data that is written to it. This was used when debugging the
 * BcelUtil to figure out where and how it differed from java.io.ObjectStreamClass.  I also had a hacked version of the
 * ObjectStreamClass that wrote it's data through and instance of this class.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class DebugOutputStream extends OutputStream {

    private static final Logger LOG          = Logger.getLogger(DebugOutputStream.class);
    private OutputStream     outputStream = null;
    private long             callNumber   = 0;
    private long             total        = 0;

    /**
     * Constructor DebugOutputStream
     *
     * @param outputStream
     */
    public DebugOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    /**
     * Method write
     *
     * @param b .
     *
     * @throws IOException
     */
    public void write(byte[] b) throws IOException {

        StringBuffer sb = new StringBuffer();

        sb.append("write(byte[]");

        for (int i = 0; i < b.length; i++)
        {
            sb.append(" ");
            sb.append(Integer.toHexString(b[i]));

            total += b[i];
        }

        sb.append(")");
        LOG.info((callNumber++) + " " + sb + " T=" + total);
        outputStream.write(b);
    }


    /**
     * Method write
     *
     * @param b .
     * @param off .
     * @param len .
     *
     * @throws IOException
     */
    public void write(byte[] b, int off, int len) throws IOException {

        StringBuffer sb = new StringBuffer();

        sb.append("write(byte[] ");

        for (int i = 0; i < len; i++)
        {
            sb.append(" ");
            sb.append(Integer.toHexString(b[i + off]));

            total += b[i + off];
        }

        sb.append(", " + off + ", " + len + ")");
        LOG.info((callNumber++) + " " + sb + " T=" + total);
        outputStream.write(b, off, len);
    }


    /**
     * Method flush
     *
     * @throws IOException
     */
    public void flush() throws IOException {
        LOG.info("flush()");
        outputStream.flush();
    }


    /**
     * Method close
     *
     * @throws IOException
     */
    public void close() throws IOException {
        LOG.info("close()");
        outputStream.close();
    }


    /**
     * Method write
     *
     * @param b .
     *
     * @throws IOException
     */
    public void write(int b) throws IOException {

        total += b;

        LOG.info((callNumber++) + " write(" + Integer.toHexString(b) + ") T=" + total);
        outputStream.write(b);
    }
}
