package org.shiftone.jrat.util.io;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import org.shiftone.jrat.util.log.Logger;

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

    @Override
    public void write(int c) throws IOException {
        printStream.print(c);
    }

    @Override
    public void write(char cbuf[]) throws IOException {
        printStream.print(cbuf);
    }

    @Override
    public void write(String str) throws IOException {
        printStream.print(str);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        printStream.print(str.substring(off, off + len));
    }

    @Override
    public void close() throws IOException {
        printStream.close();
    }

    @Override
    public void flush() throws IOException {
        printStream.flush();
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {

        char[] target = new char[len];

        System.arraycopy(cbuf, off, target, 0, len);
        printStream.print(target);
    }
}
