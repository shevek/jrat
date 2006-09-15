package org.shiftone.jrat.provider.trace.ui;



import org.shiftone.jrat.core.MethodKey;

import javax.swing.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


public class TraceNode implements TreeNode {

    private MethodKey methodKey;
    private int       durationMs;
    private boolean   success;
    private List      children = new ArrayList(10);
    private TraceNode parent;

    public TraceNode(TraceNode parent) {
        this.parent = parent;
    }


    public void add(TraceNode node) {
        children.add(node);
    }


    public int getDurationMs() {
        return durationMs;
    }


    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }


    public TraceNode getNodeParent() {
        return parent;
    }


    public MethodKey getMethodKey() {
        return methodKey;
    }


    public void setMethodKey(MethodKey methodKey) {
        this.methodKey = methodKey;
    }


    public TraceNode removeChild(int index) {
        return (TraceNode) children.remove(index);
    }


    public final boolean isRootNode() {
        return (methodKey == null);
    }


    public TraceNode getTraceNode(int childIndex) {
        return (TraceNode) children.get(childIndex);
    }


    // ----------------------------------------------------
    public String toString() {

        return (methodKey == null)
               ? "root"
               : (methodKey.toString() + " - " + durationMs + "ms");
    }


    public TreeNode getChildAt(int childIndex) {
        return getTraceNode(childIndex);
    }


    public int getChildCount() {
        return children.size();
    }


    public TreeNode getParent() {
        return getNodeParent();
    }


    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }


    public boolean getAllowsChildren() {
        return true;
    }


    public boolean isLeaf() {
        return children.isEmpty();
    }


    public Enumeration children() {
        return Collections.enumeration(children);
    }
}
