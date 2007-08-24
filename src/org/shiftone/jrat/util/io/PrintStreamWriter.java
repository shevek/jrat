package org.shiftone.jrat.util.io;


import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class PrintStreamWriter extends Writer {

    private static final Logger LOG = Logger.getLogger(PrintStreamWriter.class);
    private final PrintStream printStream;

    public PrintStreamWriter(PrintStream printStream) {

        super(printStream);

        this.printStream = printStream;
    }


    public void write(int c) throws IOException {
        printStream.print(c);
    }


    public void write(char cbuf[]) throws IOException {
        printStream.print(cbuf);
    }


    public void write(String str) throws IOException {
        printStream.print(str);
    }


    public void write(String str, int off, int len) throws IOException {
        printStream.print(str.substring(off, off + len));
    }


    public void close() throws IOException {
        printStream.close();
    }


    public void flush() throws IOException {
        printStream.flush();
    }


    public void write(char cbuf[], int off, int len) throws IOException {

        char[] target = new char[len];

        System.arraycopy(cbuf, off, target, 0, len);
        printStream.print(target);
    }
}
