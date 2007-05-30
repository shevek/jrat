package org.shiftone.jrat.util.io.nop;


import java.io.IOException;
import java.io.Writer;


/**
 * @author Jeff Drost
 */
public class NullWriter extends Writer {

    public static final Writer INSTANCE = new NullWriter();

    private NullWriter() {
    }


    public void write(int c) throws IOException {
    }


    public void write(char cbuf[]) throws IOException {
    }


    public void write(String str) throws IOException {
    }


    public void write(String str, int off, int len) throws IOException {
    }


    public void write(char cbuf[], int off, int len) throws IOException {
    }


    public void flush() throws IOException {
    }


    public void close() throws IOException {
    }
}
