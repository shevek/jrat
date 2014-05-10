package org.shiftone.jrat.util.io.nop;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class NullOutputStream extends OutputStream {

    public static final OutputStream INSTANCE = new NullOutputStream();

    private NullOutputStream() {
    }

    @Override
    public void write(int b) throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    }

    @Override
    public void write(byte[] b) throws IOException {
    }
}
