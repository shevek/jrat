package org.shiftone.jrat.provider.trace;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.trace.ui.TraceOutputViewBuilder;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.TimeUnit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class TraceOutput {

    private static final Logger LOG = Logger.getLogger(TraceOutput.class);
    private static final String HEADER = "viewer=\"" + TraceOutputViewBuilder.class.getName() + "\"\n";
    public static final byte THREAD = (byte) 'T';
    public static final byte ENTER = (byte) 'E';
    public static final byte EXIT = (byte) 'X';
    public static final byte INDEX = (byte) 'I';
    public static final byte DISBALE = (byte) 'D';
    public static final byte EOF = (byte) 'N';
    private DataOutputStream out;

    public TraceOutput(OutputStream out) {

        writeHeader(out);

        this.out = new DataOutputStream(out);
    }


    private void writeHeader(OutputStream out) {

        try {
            out.write(HEADER.getBytes());
        }
        catch (IOException e) {
            throw new JRatException("writeMethodEnter failed", e);
        }
    }


    public void writeMethodEnter(int methodIndex) {

        try {
            out.writeByte(ENTER);
            out.writeInt(methodIndex);
        }
        catch (IOException e) {
            throw new JRatException("writeMethodEnter failed", e);
        }
    }


    public void writeMethodExit(long durationNanos, boolean success) {

        try {
            out.writeByte(EXIT);
            out.writeInt((int) TimeUnit.MILLISECONDS.fromNanos(durationNanos));
            out.writeBoolean(success);
        }
        catch (IOException e) {
            throw new JRatException("writeMethodExit failed", e);
        }
    }


    public void writeMethodIndex(int methodIndex, MethodKey methodKey) {

        try {
            out.writeByte(INDEX);
            out.writeInt(methodIndex);
            out.writeUTF(methodKey.getClassName());
            out.writeUTF(methodKey.getMethodName());
            out.writeUTF(methodKey.getSignature());
        }
        catch (IOException e) {
            throw new JRatException("writeMethodIndex failed", e);
        }
    }


    /**
     * the method was disabled because it was called a total of "callCount"
     * times across all threads (not just this one).
     */
    public void writeMethodDisabled(int methodIndex, long callCount) {

        try {
            out.writeByte(DISBALE);
            out.writeInt(methodIndex);
            out.writeLong(callCount);
        }
        catch (IOException e) {
            throw new JRatException("writeMethodDisabled failed", e);
        }
    }


    public void writeThreadInfo(long threadId, String threadName, int priority, String groupName,
                                long currentTimeMillis) {

        try {
            out.writeByte(THREAD);
            out.writeLong(threadId);
            out.writeInt(priority);
            out.writeUTF(threadName);
            out.writeUTF(groupName);
            out.writeLong(currentTimeMillis);
        }
        catch (IOException e) {
            throw new JRatException("writeMethodDisabled failed", e);
        }
    }


    public void writeEOF() {

        try {
            out.writeByte(EOF);
            out.flush();
        }
        catch (IOException e) {
            throw new JRatException("writeEOF failed", e);
        }
    }
}
