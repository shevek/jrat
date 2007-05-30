package org.shiftone.jrat.util.io;


import org.shiftone.jrat.util.io.proxy.ProxyInputStream;
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
public class OpenInputStream extends ProxyInputStream {

    private static final Logger LOG = Logger.getLogger(OpenInputStream.class);
    private InputStream inputStream = null;

    public OpenInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    protected InputStream getTarget() throws IOException {

        assertOpen();

        return inputStream;
    }


    public void assertOpen() throws IOException {

        if (inputStream == null) {
            throw new IOException("InputStream is closed");
        }
    }


    /**
     * Method close does not call close() on the underlying input stream. It
     * set's a flag that is used assertions in the read methods of this class.
     */
    public void close() throws IOException {

        assertOpen();

        inputStream = null;

        // DO NOT inputStream.close();
    }
}
