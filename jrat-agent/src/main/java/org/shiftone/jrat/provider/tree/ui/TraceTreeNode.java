package org.shiftone.jrat.provider.tree.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.TreeNode;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TraceTreeNode implements javax.swing.tree.TreeNode {

    private static final Logger LOG = Logger.getLogger(TraceTreeNode.class);

    private final MethodKey methodKey;
    private final Accumulator accumulator;

    private final int depth;
    private int maxDepth;        // depth deepest child
    private final TraceTreeNode parent;
    private final List<TraceTreeNode> children = new ArrayList<TraceTreeNode>();
    private int totalChildren;   // total # of children and all children's children

    private final long totalSelfDuration;
    private final double meanSelfDuration;

    private final double pctOfMeanRootDuration;
    private double pctOfMeanParentDuration = Double.NaN;

    public TraceTreeNode(TreeNode node) {
        this(node, 0, null, node);
    }

    private TraceTreeNode(TreeNode node, int depth, TraceTreeNode parent, TreeNode root) {
        this.methodKey = node.getMethodKey();
        this.accumulator = node.getAccumulator();
        this.depth = depth;
        this.parent = parent;

        if (parent != null) {
            long parentTotalDuration = parent.accumulator.getTotalDuration();
            if (parentTotalDuration > 0)
                pctOfMeanParentDuration = (100.0 * accumulator.getTotalDuration()) / parentTotalDuration;
            else
                pctOfMeanParentDuration = Double.NaN;
        }

        long rootTotalDuration = root.getAccumulator().getTotalDuration();
        if (rootTotalDuration > 0)
            pctOfMeanRootDuration = (100.0 * accumulator.getTotalDuration()) / rootTotalDuration;
        else
            pctOfMeanRootDuration = Double.NaN;

        for (TreeNode childNode : node.getChildren()) {
            TraceTreeNode childTraceNode = new TraceTreeNode(childNode, depth + 1, this, root);
            totalChildren += (1 + childTraceNode.totalChildren);
            maxDepth = Math.max(maxDepth, 1 + childTraceNode.maxDepth);
            children.add(childTraceNode);
        }
        Collections.sort(children, TotalChildrenComparator.INSTANCE);

        { // total method duration
            long duration = accumulator.getTotalDuration();
            for (TraceTreeNode child : children) {
                duration -= child.accumulator.getTotalDuration();
            }
            totalSelfDuration = duration;
        }

        { // average method duration
            if (accumulator.getTotalExits() > 0)
                meanSelfDuration = totalSelfDuration / accumulator.getTotalExits();
            else
                meanSelfDuration = Double.NaN;
        }

//        this.rootAverageDurationNanos = (childOfRoot)
//                ? this.averageDurationNanos
//                : parent.rootAverageDurationNanos;
//
//        //
//        this.rootTotalDurationNanos = (childOfRoot)
//                ? node.getTotalDuration()
//                : parent.rootTotalDurationNanos;
//
//        if (rootAverageDurationNanos > 0) {
//            pctOfAvgRootDuration = (100.0 * averageDurationNanos) / rootAverageDurationNanos;
//        }
//
//        if (rootTotalDurationNanos > 0) {
//            pctOfRootTotalDuration = ((100.0 * node.getTotalDuration()) / rootTotalDurationNanos);
//        }
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }

    public boolean isRootNode() {
        return methodKey == null;
    }

    public Accumulator getAccumulator() {
        return accumulator;
    }

    public long getTotalSelfDuration() {
        return totalSelfDuration;
    }

    public double getMeanSelfDuration() {
        return meanSelfDuration;
    }

    public int getDepth() {
        return depth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getPctOfMeanParentDuration() {
        return pctOfMeanParentDuration;
    }

    public double getPctOfMeanRootDuration() {
        return pctOfMeanRootDuration;
    }

    // -------------------------------------------------------------
    @Override
    public String toString() {
        return (isRootNode())
                ? "Root"
                : methodKey.getMethodName();
    }

    @Override
    public Enumeration<TraceTreeNode> children() {
        return Collections.enumeration(children);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TraceTreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public javax.swing.tree.TreeNode getParent() {
        return parent;
    }

    public TraceTreeNode getParentNode() {
        return parent;
    }

    @Override
    public int getIndex(javax.swing.tree.TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean isLeaf() {
        return (getChildCount() == 0);
    }

    private static class TotalChildrenComparator implements Comparator<TraceTreeNode> {

        private static final Comparator<TraceTreeNode> INSTANCE = new TotalChildrenComparator();

        @Override
        public int compare(TraceTreeNode o1, TraceTreeNode o2) {
            int diff = o2.totalChildren - o1.totalChildren;
            return Integer.signum(diff);
        }
    }

}
