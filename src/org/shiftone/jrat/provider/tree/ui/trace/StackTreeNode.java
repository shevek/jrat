package org.shiftone.jrat.provider.tree.ui.trace;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.util.collection.ArrayEnumeration;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.tree.TreeNode;
import java.util.*;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 * @todo lazy init children
 */
public class StackTreeNode implements TreeNode {

    private static final Logger LOG = Logger.getLogger(StackTreeNode.class);

    private final MethodKey methodKey;
    private final Accumulator accumulator;

    private final int depth;
    private final StackTreeNode[] childArray;
    private final StackTreeNode parent;
    private double pctOfAvgRootDuration;
    private double pctOfAvgParentDuration;

    private int totalChildren;   // total # of children and all children's children
    private int maxDepth;        // depth deepest child


    public StackTreeNode(StackNode node) {
        this(node, 0, null, null);
    }


    private StackTreeNode(StackNode node, int depth, StackTreeNode parent, StackTreeNode root) {

        this.methodKey = node.getMethodKey();
        this.accumulator = node.getAccumulator();
        this.depth = depth;
        this.parent = parent;



        if ((parent != null) && (getTotalExits() > 0)) {

            long parentTotalDuration = parent.getTotalDuration();

            if (parentTotalDuration > 0) {
                pctOfAvgParentDuration = (100.0 * getTotalDuration()) / parentTotalDuration;
            }

        }

        if ((root != null) && (getTotalExits() > 0)) {

            long rootTotalDuration = root.getTotalDuration();

            if (rootTotalDuration > 0) {
                pctOfAvgRootDuration = (100.0 * getTotalDuration()) / rootTotalDuration;
            }

        }

        List c = node.getChildren();

        this.childArray = new StackTreeNode[c.size()];

        Iterator childIterator = node.getChildren().iterator();

        for (int i = 0; i < childArray.length; i++) {

            StackNode childNode = (StackNode) c.get(i);
            StackTreeNode childStackTreeNode = new StackTreeNode(childNode, depth + 1, this, root);

            totalChildren += (1 + childStackTreeNode.totalChildren);
            maxDepth = Math.max(maxDepth, 1 + childStackTreeNode.maxDepth);

            childArray[i] = childStackTreeNode;
        }

        Arrays.sort(childArray, TotalChildrenComparator.INSTANCE);

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


    public int getMaxDepth() {
        return maxDepth;
    }

    public int getDepth() {
        return depth;
    }


    public double getPctOfAvgParentDuration() {
        return pctOfAvgParentDuration;
    }


    public double getPctOfAvgRootDuration() {
        return pctOfAvgRootDuration;
    }

    // ----------------------------------------------------------


    public int getMaxConcurrentThreads() {
        return accumulator.getMaxConcurrentThreads();
    }

    public long getSumOfSquares() {
        return accumulator.getSumOfSquares();
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }

    public Float getAverageDuration() {
        return accumulator.getAverageDuration();
    }

    public Double getStdDeviation() {
        return accumulator.getStdDeviation();
    }

    public long getTotalDuration() {
        return accumulator.getTotalDuration();
    }

    public int getConcurrentThreads() {
        return accumulator.getConcurrentThreads();
    }

    public long getTotalErrors() {
        return accumulator.getTotalErrors();
    }

    public long getTotalEnters() {
        return accumulator.getTotalEnters();
    }

    public long getTotalExits() {
        return accumulator.getTotalExits();
    }

    public long getMinDuration() {
        return accumulator.getMinDuration();
    }


    public long getMaxDuration() {
        return accumulator.getMaxDuration();
    }

    public boolean isRootNode() {
        return methodKey == null;
    }


    // -------------------------------------------------------------
    public String toString() {

        return (isRootNode())
                ? "Root"
                 : methodKey.getMethodName();
    }


    public Enumeration children() {
        return new ArrayEnumeration(childArray);
    }


    public boolean getAllowsChildren() {
        return true;
    }


    public TreeNode getChildAt(int childIndex) {
        return childArray[childIndex];
    }

    public int getChildCount() {
        return childArray.length;
    }


    public TreeNode getParent() {
        return parent;
    }

    public StackTreeNode getParentNode() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        for (int i = 0; i < childArray.length; i++) {
            if (node == childArray[i]) {
                return i;
            }
        }
        return -1;
    }


    public boolean isLeaf() {
        return (getChildCount() == 0);
    }


    private static class TotalChildrenComparator implements Comparator {

        private static final Comparator INSTANCE = new TotalChildrenComparator();

        public int compare(Object o1, Object o2) {

            StackTreeNode stn1 = (StackTreeNode) o1;
            StackTreeNode stn2 = (StackTreeNode) o2;
            int diff = stn1.totalChildren - stn2.totalChildren;

            return (diff == 0)
                    ? 0
                    : (diff < 0)
                    ? 1
                    : -1;
        }
    }

    ;
}
