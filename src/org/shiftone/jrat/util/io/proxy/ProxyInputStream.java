package org.shiftone.jrat.util.io.proxy;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Jeff Drost
 */
public abstract class ProxyInputStream extends InputStream {

    private static final Logger LOG = Logger.getLogger(ProxyInputStream.class);

    protected abstract InputStream getTarget() throws IOException;


    private InputStream getTargetRTE() {

        try {
            return getTarget();
        }
        catch (IOException e) {
            throw new JRatException("failed to get target InputStream", e);
        }
    }


    public int read() throws IOException {
        return getTarget().read();
    }


    public int read(byte[] b) throws IOException {
        return getTarget().read(b);
    }


    public int read(byte[] b, int off, int len) throws IOException {
        return getTarget().read(b, off, len);
    }


    public long skip(long n) throws IOException {
        return getTarget().skip(n);
    }


    public int available() throws IOException {
        return getTarget().available();
    }


    public void close() throws IOException {
        getTarget().close();
    }


    public void mark(int readlimit) {
        getTargetRTE().mark(readlimit);
    }


    public void reset() throws IOException {
        getTarget().reset();
    }


    public boolean markSupported() {
        return getTargetRTE().markSupported();
    }
}
