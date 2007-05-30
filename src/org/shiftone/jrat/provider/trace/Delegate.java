package org.shiftone.jrat.provider.trace;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.collection.FlagStack;
import org.shiftone.jrat.util.log.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author Jeff Drost
 */
public class Delegate implements ShutdownListener {

    private static final Logger LOG = Logger.getLogger(Delegate.class);
    private RuntimeContext context;
    private int callsThreshold = 1000;
    private Map methodKeyCache = new HashMap();
    private Set methodKeyBlackList = new HashSet();
    private AtomicLong methodSequence = new AtomicLong();
    private FlagStack enterStack = new FlagStack();
    private TraceOutput out;

    public Delegate(RuntimeContext context) {
        this.context = context;
    }


    public void shutdown() {

        // this will write a EOF record and flush the file, but will not
        // actually close the file
        // more events may be written to the file after the EOF.
        getTraceOutput().writeEOF();
    }


    public TraceOutput getTraceOutput() {

        if (out == null) {
            Thread thread = Thread.currentThread();
            long threadId = thread.getId();
            int priority = thread.getPriority();
            String name = thread.getName();
            ThreadGroup group = thread.getThreadGroup();
            String groupName = (group == null)
                    ? null
                    : group.getName();
            long now = System.currentTimeMillis();

            LOG.info("threadId = " + threadId);
            LOG.info("name = " + name);
            LOG.info("groupName = " + groupName);

            out = new TraceOutput(context.createOutputStream("ThreadTrace.jrat"));

            out.writeThreadInfo(threadId, name, priority, groupName, now);
            context.registerShutdownListener(this);
        }

        return out;
    }


    private int getMethodKeyId(MethodKey methodKey) {

        Integer id = (Integer) methodKeyCache.get(methodKey);

        if (id == null) {
            id = new Integer((int) methodSequence.incrementAndGet());

            methodKeyCache.put(methodKey, id);
            getTraceOutput().writeMethodIndex(id.intValue(), methodKey);
        }

        return id.intValue();
    }


    /**
     * @param methodCallCount -
     *                        the number of total times this method has been called from any
     *                        thread
     */
    public void onMethodStart(MethodKey methodKey, long methodCallCount) {

        TraceOutput out = getTraceOutput();

        if (methodCallCount > callsThreshold) {
            if (methodKeyBlackList.add(methodKey)) {
                LOG.info(methodCallCount + " calls to method " + methodKey + ".  Will now ignore this method.");
                out.writeMethodDisabled(getMethodKeyId(methodKey), methodCallCount);
            }

            enterStack.push(false);
        } else if (!methodKeyBlackList.contains(methodKey)) {
            out.writeMethodEnter(getMethodKeyId(methodKey));
            enterStack.push(true);
        }
    }


    public void onMethodFinish(long durationNanos, Throwable throwable) {

        if (enterStack.pop()) {
            getTraceOutput().writeMethodExit(durationNanos, throwable == null);
        }
    }
}
