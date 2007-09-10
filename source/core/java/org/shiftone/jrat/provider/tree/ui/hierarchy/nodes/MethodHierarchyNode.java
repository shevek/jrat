package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;

import java.util.Collections;
import java.util.List;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MethodHierarchyNode extends HierarchyNode {

    public MethodHierarchyNode(MethodKey methodKey, MethodSummaryModel methodSummaryModel) {

        super(methodKey.getMethodName()
                + "("
                + methodKey.getSig().getShortText()
                + ")",
                methodSummaryModel);
    }

    public void finalizeStatistics() {
    }

    public Long getTotalMethodDuration() {
        return null;
    }

    public boolean isExecuted() {
        return false;
    }

    public long getTotalDuration() {
        return 0;
    }

    public int getTotalMethods() {
        return 1;
    }

    public int getExecutedMethods() {
        return 0;
    }

    public long getTotalErrors() {
        return 0;
    }

    public long getTotalExits() {
        return 0;
    }

    public List getChildren() {
        return Collections.emptyList();
    }
}
