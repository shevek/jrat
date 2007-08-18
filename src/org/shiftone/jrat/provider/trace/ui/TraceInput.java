package org.shiftone.jrat.provider.trace.ui;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.trace.TraceOutput;
import org.shiftone.jrat.util.log.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author Jeff Drost
 */
public class TraceInput {

    private static final Logger LOG = Logger.getLogger(TraceInput.class);
    private final DataInput in;
    private TraceNode current;
    private Set blackList = new HashSet();
    private Map methodKeys = new HashMap();
    private TraceModel traceModel = new TraceModel(new TraceNode(null), blackList);

    public TraceInput(InputStream inputStream) {

        try {
            while (inputStream.read() != '\n') ;

            in = new DataInputStream(inputStream);
        }
        catch (IOException e) {
            throw new JRatException("error reading header", e);
        }
    }


    public TraceModel read() throws IOException {

        current = traceModel.getRootNode();

        boolean complete = false;

        try {
            while (!complete) {
                byte b = in.readByte();

                // LOG.info("switch " + (char) b);
                switch (b) {

                    case TraceOutput.ENTER:
                        readEnter();
                        break;

                    case TraceOutput.EXIT:
                        readExit();
                        break;

                    case TraceOutput.INDEX:
                        readIndex();
                        break;

                    case TraceOutput.DISBALE:
                        readDisable();
                        break;

                    case TraceOutput.THREAD:
                        readThreadInfo();
                        break;

                    case TraceOutput.EOF:
                        complete = true;
                }
            }
        }
        catch (EOFException e) {
            LOG.info("file was not closed correctly", e);
        }

        pruneBlacklistNodes(traceModel.getRootNode());

        return traceModel;
    }


    private void pruneBlacklistNodes(TraceNode traceNode) {

        for (int i = traceNode.getChildCount() - 1; i >= 0; i--) {
            TraceNode child = traceNode.getTraceNode(i);

            if (blackList.contains(child.getMethodKey())) {

                // LOG.info("remove " + child.getMethodKey());
                traceNode.removeChild(i);
            } else {
                pruneBlacklistNodes(child);
            }
        }
    }


    private void readEnter() throws IOException {

        MethodKey methodName = getMethodKey(in.readInt());
        TraceNode child = new TraceNode(current);

        child.setMethodKey(methodName);
        current.add(child);

        current = child;
    }


    private void readExit() throws IOException {

        current.setDurationMs(in.readInt());
        current.setSuccess(in.readBoolean());

        current = current.getNodeParent();
    }


    private void readIndex() throws IOException {

        int methodIndex = in.readInt();
        String className = in.readUTF();
        String methodName = in.readUTF();
        String signature = in.readUTF();
        MethodKey methodKey = MethodKey.create(className, methodName, signature);

        methodKeys.put(new Integer(methodIndex), methodKey);
    }


    private MethodKey getMethodKey(int index) {
        return (MethodKey) methodKeys.get(new Integer(index));
    }


    private void readThreadInfo() throws IOException {

        traceModel.setThreadId(in.readLong());
        traceModel.setPriority(in.readInt());
        traceModel.setThreadName(in.readUTF());
        traceModel.setGroupName(in.readUTF());
        traceModel.setRunTime(in.readLong());
    }


    private void readDisable() throws IOException {

        MethodKey methodKey = getMethodKey(in.readInt());
        long callCount = in.readLong();

        // LOG.info("disable " + methodKey);
        blackList.add(methodKey);
    }
}
