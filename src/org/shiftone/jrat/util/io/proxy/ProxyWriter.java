package org.shiftone.jrat.util.io.proxy;



import java.io.IOException;
import java.io.Writer;


public abstract class ProxyWriter extends Writer {

    protected abstract Writer getTarget() throws IOException;


    public void write(int c) throws IOException {
        getTarget().write(c);
    }


    public void write(char cbuf[]) throws IOException {
        getTarget().write(cbuf);
    }


    public void write(String str) throws IOException {
        getTarget().write(str);
    }


    public void write(String str, int off, int len) throws IOException {
        getTarget().write(str, off, len);
    }


    public void write(char cbuf[], int off, int len) throws IOException {
        getTarget().write(cbuf, off, len);
    }


    public void flush() throws IOException {
        getTarget().flush();
    }


    public void close() throws IOException {
        getTarget().close();
    }
}
