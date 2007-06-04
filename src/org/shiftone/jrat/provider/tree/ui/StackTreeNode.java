package org.shiftone.jrat.provider.tree.ui;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.TimeUnit;

import javax.swing.tree.TreeNode;
import java.util.*;


/**
 * Class StackTreeNode
 *
 * @author Jeff Drost
 */
public class StackTreeNode implements TreeNode {

    private static final Logger LOG = Logger.getLogger(StackTreeNode.class);

    private final StackNode node;
    private final int depth;   // replace with depth
    private final List childList ;
    private StackTreeNode parent;    
    protected double pctOfAvgParentDuration;
    protected double averageDurationNanos;
    protected double rootAverageDurationNanos;
    protected double rootTotalDurationNanos;
    protected double pctOfAvgRootDuration;
    protected double pctOfRootTotalDuration;
    private int totalChildren;
    private int maxDepth;


    public StackTreeNode(StackNode node) {
        this(node, 0);
    }


    private StackTreeNode(StackNode node, int depth) {

        LOG.info("new " + depth);
        this.node = node;
        this.depth = depth;        
        this.childList = new ArrayList(3);

        Iterator childIterator = node.getChildren().iterator();

        while (childIterator.hasNext())  {
             StackNode childNode = (StackNode)childIterator.next();
             StackTreeNode childStackTreeNode = new StackTreeNode(childNode, depth + 1);
             childStackTreeNode.parent = this;
             childList.add(childStackTreeNode);
        }


        if (node.getTotalExits() > 0) {
            averageDurationNanos = (double) node.getTotalDuration() / (double) node.getTotalExits();

            if (parent != null) {
                long parentTotalDurationNanos = parent.node.getTotalDuration();

                if (parentTotalDurationNanos > 0) {
                    pctOfAvgParentDuration = (100.0 * node.getTotalDuration()) / parentTotalDurationNanos;
                }
            }
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


    /**
     * stats are set on a node, then all the children are added to that node,
     * and then.. after all that, completeStats is called on the node. the node
     * has a chance to (1) count it's childrent and it's children's children and
     * (2) sortColumn it's children based on the total number of children below.
     * This has the result of putting more complex nodes first on the screen.
     */
    public void completeStats() {

        int maxChildDepth = 0;

        totalChildren += childList.size();

        for (int i = 0; i < childList.size(); i++) {
            StackTreeNode child = (StackTreeNode) childList.get(i);

            totalChildren += child.totalChildren;
            maxChildDepth = Math.max(maxChildDepth, child.maxDepth);
        }

        maxDepth = maxChildDepth + 1;

        Comparator comparator = new Comparator() {

            public int compare(Object o1, Object o2) {

                StackTreeNode stn1 = (StackTreeNode) o1;
                StackTreeNode stn2 = (StackTreeNode) o2;
                int diff = stn1.getTotalChildren() - stn2.getTotalChildren();

                return (diff == 0)
                        ? 0
                        : (diff < 0)
                        ? 1
                        : -1;
            }
        };

        Collections.sort(childList, comparator);
    }


    public int getMaxConcurrentThreads() {
        return node.getMaxConcurrentThreads();
    }

    public long getSumOfSquares() {
        return node.getSumOfSquares();
    }

    public MethodKey getMethodKey() {
        return node.getMethodKey();
    }

    public Long getMaxDurationNanos() {
        return node.getMaxDuration();
    }


    public Float getAverageDuration () {
        return node.getAverageDuration ();
    }

    public Double getStdDeviation() {
        return node.getStdDeviation();
    }

    public long getTotalDuration() {
        return node.getTotalDuration();
    }

    public int getConcurrentThreads() {
        return node.getConcurrentThreads();
    }


    public long getTotalErrors() {
        return node.getTotalErrors();
    }

    public long getTotalEnters() {
        return node.getTotalEnters();
    }

    public long getTotalExits() {
        return node.getTotalExits();
    }

    public Long getMinDuration() {
        return node.getMinDuration();
    }


    public Long getMaxDuration() {
        return node.getMaxDuration();
    }

    public boolean isRootNode() {
        return node.isRootNode();
    }

    public int getMaxDepth() {
        return maxDepth;
    }


//    /**
//     * Method gets <b>AND CREATES IF NEEDED</b> the requested tree node. This
//     * method does the <i>exact</i> same thing as that of the parent class,
//     * <i>except</i> new child nodes are also added to a list, which is used to
//     * support the TreeNode interface.
//     */
//    public StackNode getChild(MethodKey methodKey) {
//
//        StackNode treeNode = null;
//
//        synchronized (children) {
//            treeNode = (StackNode) children.get(methodKey);
//
//            if (treeNode == null) {
//                treeNode = new StackTreeNode(methodKey, this);
//
//                childList.add(treeNode);
//                children.put(methodKey, treeNode);
//            }
//        }
//
//        return treeNode;
//    }


    // -------------------------------------------------------------
    public int getTotalChildren() {
        return totalChildren;
    }


    public double getPctOfAvgParentDuration() {
        return pctOfAvgParentDuration;
    }


    public double getPctOfAvgRootDuration() {
        return pctOfRootTotalDuration;
    }


    public boolean isChildOfRootNode() {
        return depth == 1;
    }


    // -------------------------------------------------------------
    public String toString() {

        return (node.isRootNode())
                ? "Root"
                : node.getMethodKey().getMethodName();
    }


    public Enumeration children() {
        return Collections.enumeration(childList);
    }


    public boolean getAllowsChildren() {
        return true;
    }


    public TreeNode getChildAt(int childIndex) {
        return getStackTreeNodeAt(childIndex);
    }


    public StackTreeNode getStackTreeNodeAt(int childIndex) {
        return (StackTreeNode) childList.get(childIndex);
    }


    public int getChildCount() {
        return childList.size();
    }


    public TreeNode getParent() {
        return getParentNode();
    }


    public StackTreeNode getParentNode() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        return childList.indexOf(node);
    }


    public boolean isLeaf() {
        return (getChildCount() == 0);
    }
}
