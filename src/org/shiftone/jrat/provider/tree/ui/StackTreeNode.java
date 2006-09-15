package org.shiftone.jrat.provider.tree.ui;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;


/**
 * Class StackTreeNode
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.24 $
 */
public class StackTreeNode extends StackNode implements TreeNode {

    private static final Logger LOG = Logger.getLogger(StackTreeNode.class);

    //private static final Double ZERO = new Double(0);
    private boolean  isChildOfRoot = false;
    private List     childList     = new ArrayList(3);
    protected double pctOfAvgParentDuration;
    protected double averageDurationNanos;
    protected double rootAverageDurationNanos;
    protected double rootTotalDurationNanos;
    protected double pctOfAvgRootDuration;
    protected double pctOfRootTotalDuration;
    private int      totalChildren;
    private int      maxDepth;

    public StackTreeNode() {}


    public StackTreeNode(MethodKey methodKey, StackTreeNode parentStackTreeNode) {
        super(methodKey, parentStackTreeNode);
    }


    public void setStatistics(long totalEnters, long totalExits, long totalErrors, long totalDuration,
                              long totalOfSquares, long maxDuration, long minDuration, int maxConcurThreads) {

        super.setStatistics(totalEnters, totalExits, totalErrors, totalDuration,    //
                            totalOfSquares, maxDuration, minDuration, maxConcurThreads);

        StackTreeNode p = (StackTreeNode) parent;

        this.isChildOfRoot = p.isRootNode();

        if (totalExits > 0)
        {
            averageDurationNanos = (double) getTotalDurationNanos() / (double) getTotalExits();

            if (parent != null)
            {
                long parentTotalDurationNanos = p.getTotalDurationNanos();

                if (parentTotalDurationNanos > 0)
                {
                    pctOfAvgParentDuration = (100.0 * getTotalDurationNanos()) / parentTotalDurationNanos;
                }
            }
        }

        this.rootAverageDurationNanos = (isChildOfRoot)
                                        ? this.averageDurationNanos
                                        : p.rootAverageDurationNanos;

        //
        this.rootTotalDurationNanos = (isChildOfRoot)
                                      ? getTotalDurationNanos()
                                      : p.rootTotalDurationNanos;

        if (rootAverageDurationNanos > 0)
        {
            pctOfAvgRootDuration = (100.0 * averageDurationNanos) / rootAverageDurationNanos;
        }

        if (rootTotalDurationNanos > 0)
        {
            pctOfRootTotalDuration = ((100.0 * getTotalDurationNanos()) / rootTotalDurationNanos);
        }
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

        for (int i = 0; i < childList.size(); i++)
        {
            StackTreeNode child = (StackTreeNode) childList.get(i);

            totalChildren += child.totalChildren;
            maxChildDepth = Math.max(maxChildDepth, child.maxDepth);
        }

        maxDepth = maxChildDepth + 1;

        Comparator comparator = new Comparator() {

            public int compare(Object o1, Object o2) {

                StackTreeNode stn1 = (StackTreeNode) o1;
                StackTreeNode stn2 = (StackTreeNode) o2;
                int           diff = stn1.getTotalChildren() - stn2.getTotalChildren();

                return (diff == 0)
                       ? 0
                       : (diff < 0)
                         ? 1
                         : -1;
            }
        };

        Collections.sort(childList, comparator);
    }


    public int getMaxDepth() {
        return maxDepth;
    }


    /**
     * Method gets <b>AND CREATES IF NEEDED</b> the requested tree node. This
     * method does the <i>exact</i> same thing as that of the parent class,
     * <i>except</i> new child nodes are also added to a list, which is used to
     * support the TreeNode interface.
     */
    public StackNode getChild(MethodKey methodKey) {

        StackNode treeNode = null;

        synchronized (children)
        {
            treeNode = (StackNode) children.get(methodKey);

            if (treeNode == null)
            {
                treeNode = new StackTreeNode(methodKey, this);

                childList.add(treeNode);
                children.put(methodKey, treeNode);
            }
        }

        return treeNode;
    }


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
        return isChildOfRoot;
    }


    // -------------------------------------------------------------
    public String toString() {

        return (isRootNode())
               ? "Root"
               : methodKey.getMethodName();
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
        return (TreeNode) getParentNode();
    }


    public int getIndex(TreeNode node) {
        return childList.indexOf(node);
    }


    public boolean isLeaf() {
        return (getChildCount() == 0);
    }
}
