package org.shiftone.jrat.util.io.proxy;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ProxyOutputStream extends OutputStream {

    protected abstract OutputStream getTarget() throws IOException;

    @Override
    public void write(byte b[]) throws IOException {
        getTarget().write(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        getTarget().write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        getTarget().flush();
    }

    @Override
    public void close() throws IOException {
        getTarget().close();
    }

    @Override
    public void write(int b) throws IOException {
        getTarget().write(b);
    }
}
