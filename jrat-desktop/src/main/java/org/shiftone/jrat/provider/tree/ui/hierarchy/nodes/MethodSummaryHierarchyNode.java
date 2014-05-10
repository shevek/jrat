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

    public boolean isExecuted() {
        return true;
    }

    public int getExecutedMethods() {
        return 1;
    }

    public long getTotalDuration() {
        return methodSummary.getTotalDuration();
    }

    public Long getTotalMethodDuration() {
        return methodSummary.getTotalMethodDuration();
    }

    public MethodSummary getMethodSummary() {
        return methodSummary;
    }

    public long getTotalExits() {
        return methodSummary.getTotalExists();
    }

    public long getTotalErrors() {
        return methodSummary.getTotalErrors();
    }
}
