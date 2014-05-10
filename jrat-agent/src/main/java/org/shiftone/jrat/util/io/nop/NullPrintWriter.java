package org.shiftone.jrat.util.io.nop;

import java.io.PrintWriter;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class NullPrintWriter extends PrintWriter {

    public static final PrintWriter INSTANCE = new NullPrintWriter();

    private NullPrintWriter() {
        super(NullWriter.INSTANCE);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    @Override
    public boolean checkError() {
        return false;
    }

    @Override
    protected void setError() {
    }

    @Override
    public void write(int c) {
    }

    @Override
    public void write(char buf[], int off, int len) {
    }

    @Override
    public void write(char buf[]) {
    }

    @Override
    public void write(String s, int off, int len) {
    }

    @Override
    public void write(String s) {
    }

    @Override
    public void print(boolean b) {
    }

    @Override
    public void print(char c) {
    }

    @Override
    public void print(int i) {
    }

    @Override
    public void print(long l) {
    }

    @Override
    public void print(float f) {
    }

    @Override
    public void print(double d) {
    }

    @Override
    public void print(char s[]) {
    }

    @Override
    public void print(String s) {
    }

    @Override
    public void print(Object obj) {
    }

    @Override
    public void println() {
    }

    @Override
    public void println(boolean x) {
    }

    @Override
    public void println(char x) {
    }

    @Override
    public void println(int x) {
    }

    @Override
    public void println(long x) {
    }

    @Override
    public void println(float x) {
    }

    @Override
    public void println(double x) {
    }

    @Override
    public void println(char x[]) {
    }

    @Override
    public void println(String x) {
    }

    @Override
    public void println(Object x) {
    }
}
