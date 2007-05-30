package org.shiftone.jrat.util.io.nop;



import java.io.PrintWriter;


/**
 * @author Jeff Drost
 *
 */
public class NullPrintWriter extends PrintWriter {

    public static final PrintWriter INSTANCE = new NullPrintWriter();

    private NullPrintWriter() {
        super(NullWriter.INSTANCE);
    }


    public void flush() {}


    public void close() {}


    public boolean checkError() {
        return false;
    }


    protected void setError() {}


    public void write(int c) {}


    public void write(char buf[], int off, int len) {}


    public void write(char buf[]) {}


    public void write(String s, int off, int len) {}


    public void write(String s) {}


    public void print(boolean b) {}


    public void print(char c) {}


    public void print(int i) {}


    public void print(long l) {}


    public void print(float f) {}


    public void print(double d) {}


    public void print(char s[]) {}


    public void print(String s) {}


    public void print(Object obj) {}


    public void println() {}


    public void println(boolean x) {}


    public void println(char x) {}


    public void println(int x) {}


    public void println(long x) {}


    public void println(float x) {}


    public void println(double x) {}


    public void println(char x[]) {}


    public void println(String x) {}


    public void println(Object x) {}
}
