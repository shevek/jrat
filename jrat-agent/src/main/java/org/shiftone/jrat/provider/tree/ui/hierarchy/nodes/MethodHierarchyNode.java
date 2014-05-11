package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import java.util.Collections;
import java.util.List;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;

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

    @Override
    public void finalizeStatistics() {
    }

    @Override
    public Long getTotalMethodDuration() {
        return null;
    }

    public boolean isExecuted() {
        return false;
    }

    @Override
    public long getTotalDuration() {
        return 0;
    }

    @Override
    public int getTotalMethods() {
        return 1;
    }

    @Override
    public int getExecutedMethods() {
        return 0;
    }

    @Override
    public long getTotalErrors() {
        return 0;
    }

    @Override
    public long getTotalExits() {
        return 0;
    }

    @Override
    public List getChildren() {
        return Collections.emptyList();
    }
}
