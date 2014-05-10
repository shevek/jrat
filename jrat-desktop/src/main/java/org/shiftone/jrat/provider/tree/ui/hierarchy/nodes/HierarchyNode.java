package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;
import org.shiftone.jrat.util.Percent;

import java.util.List;

public abstract class HierarchyNode {

    private final MethodSummaryModel methodSummaryModel;
    private static final HierarchyNode[] EMPTY = {};
    private final String name;


    public HierarchyNode(String name, MethodSummaryModel methodSummaryModel) {
        this.name = name;
        this.methodSummaryModel = methodSummaryModel;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }


    public MethodSummaryModel getMethodSummaryModel() {
        return methodSummaryModel;
    }

    public abstract void finalizeStatistics();

    public abstract long getTotalDuration();

    public abstract int getTotalMethods();

    public abstract int getExecutedMethods();

    public abstract long getTotalExits();

    public abstract long getTotalErrors();

    public abstract Long getTotalMethodDuration();

    public Percent getErrorRate() {
        return new Percent((double) getTotalErrors() * 100.0 / (double) getTotalExits());
    }

    public Percent getCoverage() {
        return new Percent((double) getExecutedMethods() * 100.0 / (double) getTotalMethods());
    }

    public int getUncalledMethods() {
        return getTotalMethods() - getExecutedMethods();
    }

    public Percent getTotalMethodPercent() {
        Long tmd = getTotalMethodDuration();
        return (tmd == null)
                ? null
                : new Percent((double) tmd.longValue() * 100.0 / (double) methodSummaryModel.getTotalMethodDuration());
    }

    public abstract List getChildren();

    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    public int getIndexOfChild(HierarchyNode node) {
        for (int i = 0; i < getChildCount(); i++) {
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
