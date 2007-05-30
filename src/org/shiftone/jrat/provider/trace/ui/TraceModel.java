package org.shiftone.jrat.provider.trace.ui;


import java.util.Set;


/**
 * @author Jeff Drost
 */
public class TraceModel {

    private long threadId;
    private int priority;
    private String threadName;
    private String groupName;
    private long runTime;
    private TraceNode rootNode;
    private Set methodKeyBlackList;

    public TraceModel(TraceNode traceNode, Set methodKeyBlackList) {
        this.rootNode = traceNode;
        this.methodKeyBlackList = methodKeyBlackList;
    }


    public TraceNode getRootNode() {
        return rootNode;
    }


    public Set getMethodKeyBlackList() {
        return methodKeyBlackList;
    }


    public long getThreadId() {
        return threadId;
    }


    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }


    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }


    public String getThreadName() {
        return threadName;
    }


    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }


    public String getGroupName() {
        return groupName;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public long getRunTime() {
        return runTime;
    }


    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }
}
