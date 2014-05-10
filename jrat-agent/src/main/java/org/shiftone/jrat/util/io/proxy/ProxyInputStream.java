package org.shiftone.jrat.util.io.proxy;

import java.io.IOException;
import java.io.InputStream;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public abstract class ProxyInputStream extends InputStream {

    private static final Logger LOG = Logger.getLogger(ProxyInputStream.class);

    protected abstract InputStream getTarget() throws IOException;

    private InputStream getTargetRTE() {

        try {
            return getTarget();
        } catch (IOException e) {
            throw new JRatException("failed to get target InputStream", e);
        }
    }

    @Override
    public int read() throws IOException {
        return getTarget().read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return getTarget().read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return getTarget().read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return getTarget().skip(n);
    }

    @Override
    public int available() throws IOException {
        return getTarget().available();
    }

    @Override
    public void close() throws IOException {
        getTarget().close();
    }

    @Override
    public void mark(int readlimit) {
        getTargetRTE().mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        getTarget().reset();
    }

    @Override
    public boolean markSupported() {
        return getTargetRTE().markSupported();
    }
}
