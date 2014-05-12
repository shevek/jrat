package org.shiftone.jrat.util.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.shiftone.jrat.util.log.Logger;

/**
 * Class OpenInputStream wraps/proxies to a real InputStream and prevents the
 * caller from closing the underlying input stream. This is useful when reading
 * chunks from a ZipInputStream passing the archive entry inputStreams to code
 * that calls close(). This would typically close the entire ZipInputStream,
 * which would prevent any other archive entries from being read.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class OpenInputStream extends FilterInputStream {

    private static final Logger LOG = Logger.getLogger(OpenInputStream.class);

    public OpenInputStream(InputStream in) {
        super(in);
    }

    /**
     * Method close does not call close() on the underlying input stream. It
     * set's a flag that is used assertions in the read methods of this class.
     */
    @Override
    public void close() throws IOException {
        in = null;
        // DO NOT inputStream.close();
    }
}
