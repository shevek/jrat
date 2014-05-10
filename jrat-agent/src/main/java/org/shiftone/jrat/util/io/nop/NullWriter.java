package org.shiftone.jrat.util.io.nop;

import java.io.IOException;
import java.io.Writer;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class NullWriter extends Writer {

    public static final Writer INSTANCE = new NullWriter();

    private NullWriter() {
    }

    @Override
    public void write(int c) throws IOException {
    }

    @Override
    public void write(char cbuf[]) throws IOException {
    }

    @Override
    public void write(String str) throws IOException {
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
}
