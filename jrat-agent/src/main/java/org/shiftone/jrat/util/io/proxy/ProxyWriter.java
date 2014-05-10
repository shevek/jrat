package org.shiftone.jrat.util.io.proxy;

import java.io.IOException;
import java.io.Writer;

public abstract class ProxyWriter extends Writer {

    protected abstract Writer getTarget() throws IOException;

    @Override
    public void write(int c) throws IOException {
        getTarget().write(c);
    }

    @Override
    public void write(char cbuf[]) throws IOException {
        getTarget().write(cbuf);
    }

    @Override
    public void write(String str) throws IOException {
        getTarget().write(str);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        getTarget().write(str, off, len);
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {
        getTarget().write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        getTarget().flush();
    }

    @Override
    public void close() throws IOException {
        getTarget().close();
    }
}
