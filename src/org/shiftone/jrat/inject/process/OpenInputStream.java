package org.shiftone.jrat.inject.process;


import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.InputStream;


/**
 * Class OpenInputStream wrapps/proxies to a real InputStream and prevents the
 * caller from closing the underlying input stream. This is useful when reading
 * chunks from a ZipInputStream passing the archive entry inputStreams to code
 * that calls close(). This would typically close the entire ZipInputStream,
 * which would prevent any other archive entries from being read.
 *
 * @author Jeff Drost
 */
public class OpenInputStream extends InputStream {

    private static final Logger LOG = Logger.getLogger(OpenInputStream.class);
    private InputStream inputStream = null;
    private boolean isOpen = true;

    public OpenInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public void assertOpen() throws IOException {

        if (isOpen == false) {
            throw new IOException("InputStream is closed");
        }
    }


    public int available() throws IOException {
        return inputStream.available();
    }


    /**
     * Method close does not call close() on the underlying input stream. It
     * set's a flag that is used assertions in the read methods of this class.
     */
    public void close() throws IOException {

        assertOpen();

        isOpen = false;

        // DO NOT inputStream.close();
    }


    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }


    public boolean markSupported() {
        return inputStream.markSupported();
    }


    public int read() throws IOException {

        assertOpen();

        return inputStream.read();
    }


    public int read(byte b[]) throws IOException {

        assertOpen();

        return inputStream.read(b);
    }


    public int read(byte b[], int off, int len) throws IOException {

        assertOpen();

        return inputStream.read(b, off, len);
    }


    public synchronized void reset() throws IOException {
        assertOpen();
        inputStream.reset();
    }


    public long skip(long n) throws IOException {

        assertOpen();

        return inputStream.skip(n);
    }
}
