package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.util.Percent;

import java.util.List;

public abstract class HierarchyNode {

    private static final HierarchyNode[] EMPTY = {};
    private final String name;


    public HierarchyNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }


    public abstract void finalizeStatistics();

    public abstract long getTotalDurationMs();

    public abstract int getTotalMethods();

    public abstract int getExecutedMethods();

    public Percent getCoverage() {

        return new Percent((double) getExecutedMethods() / (double) getTotalMethods() * 100.0);
    }

    public int getUncalledMethods() {
        return getTotalMethods() - getExecutedMethods();
    }

    public abstract List getChildren();

    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    public int getIndexOfChild(HierarchyNode node) {
        for (int i = 0; i < getChildCount(); i ++) {
            if (node == getChild(i)) {
                return i;
            }
        }
        return -1;
    }

    public HierarchyNode getChild(int index) {
        return (HierarchyNode) getChildren().get(index);
    }

    public int getChildCount() {
        return getChildren().size();
    }
}
