package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.provider.tree.ui.summary.MethodSummary;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodSummaryHierarchyNode extends MethodHierarchyNode {

    private final MethodSummary methodSummary;

    public MethodSummaryHierarchyNode(MethodSummary methodSummary, MethodSummaryModel methodSummaryModel
    ) {

        super(methodSummary.getMethodKey(), methodSummaryModel);

        this.methodSummary = methodSummary;
    }

    @Override
    public boolean isExecuted() {
        return true;
    }

    @Override
    public int getExecutedMethods() {
        return 1;
    }

    @Override
    public long getTotalDuration() {
        return methodSummary.getTotalDuration();
    }

    @Override
    public Long getTotalMethodDuration() {
        return methodSummary.getTotalSelfDuration();
    }

    public MethodSummary getMethodSummary() {
        return methodSummary;
    }

    @Override
    public long getTotalExits() {
        return methodSummary.getTotalExits();
    }

    @Override
    public long getTotalErrors() {
        return methodSummary.getTotalErrors();
    }
}
