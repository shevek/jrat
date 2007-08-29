package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummary;

import java.util.Collections;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodSummaryHierarchyNode extends MethodHierarchyNode {

    private final MethodSummary methodSummary;

    public MethodSummaryHierarchyNode(MethodSummary methodSummary) {

        super(methodSummary.getMethodKey());

        this.methodSummary = methodSummary;
    }

    public boolean isExecuted() {
        return true;
    }

    public int getExecutedMethods() {
        return 1;
    }

    public long getTotalDurationMs() {
        return methodSummary.getTotalDuration();
    }

}
