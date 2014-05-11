package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ClassHierarchyNode extends HierarchyNode {

    private final List childMethods = new ArrayList();

    private int executedMethods;
    private long totalDurationMs;
    private long totalMethodDuration;
    private long totalErrors;
    private long totalExists;

    public ClassHierarchyNode(String name, MethodSummaryModel methodSummaryModel) {
        super(name, methodSummaryModel);
    }

    @Override
    public void finalizeStatistics() {

        for (Iterator i = childMethods.iterator(); i.hasNext();) {
            MethodHierarchyNode method = (MethodHierarchyNode) i.next();
            totalDurationMs += method.getTotalDuration();
            totalErrors += method.getTotalErrors();
            totalExists += method.getTotalExits();

            if (method.isExecuted()) {
                executedMethods++;
            }

            Long tmd = method.getTotalMethodDuration();
            if (tmd != null) {
                totalMethodDuration += tmd.longValue();
            }
        }
    }

    public void addMethod(MethodHierarchyNode methodNode) {
        childMethods.add(methodNode);
    }

    @Override
    public List getChildren() {
        return childMethods;
    }

    @Override
    public int getTotalMethods() {
        return childMethods.size();
    }

    @Override
    public int getExecutedMethods() {
        return executedMethods;
    }

    @Override
    public long getTotalDuration() {
        return totalDurationMs;
    }

    @Override
    public long getTotalExits() {
        return totalExists;
    }

    @Override
    public long getTotalErrors() {
        return totalErrors;
    }

    @Override
    public Long getTotalMethodDuration() {
        return new Long(totalMethodDuration);
    }
}
