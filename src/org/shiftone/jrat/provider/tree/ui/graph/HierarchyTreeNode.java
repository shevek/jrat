package org.shiftone.jrat.provider.tree.ui.graph;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author Jeff Drost
 */
public class HierarchyTreeNode implements TreeNode {

    private HierarchyTreeNode parent ;
    private List children = new ArrayList();


    public HierarchyTreeNode(HierarchyTreeNode parent) {
        this.parent = parent;
    }

    public HierarchyTreeNode getChildAt(int childIndex) {
        return (HierarchyTreeNode)children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }

    public HierarchyTreeNode getParent() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        return parent.getIndex(this);
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
