package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;

import java.util.Collections;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodHierarchyNode extends HierarchyNode {

    private final MethodKey methodKey;
    private boolean entered;
    private boolean exited;
    private long totalDurationMs;

    public MethodHierarchyNode(MethodKey methodKey) {
        super(methodKey.getMethodName() + "(" + methodKey.getSig().getShortText() + ")");
        this.methodKey = methodKey;
    }


    public void addStatistics(StackNode input) {
        Accumulator accumulator = input.getAccumulator();
        entered = entered || accumulator.getTotalEnters() > 0;
        exited = exited || accumulator.getTotalExits() > 0;
        totalDurationMs += accumulator.getTotalDuration();
    }

    public boolean isEntered() {
        return entered;
    }

    public boolean isExited() {
        return exited;
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }

    public void finalizeStatistics() {
        // nothing to do
    }

    public int getTotalMethods() {
        return 1;
    }

    public int getEnteredMethods() {
        return entered ? 1 : 0;
    }

    public int getExistedMethods() {
        return exited ? 1 : 0;
    }

    public long getTotalDurationMs() {
        return totalDurationMs;
    }

    public List getChildren() {
        return Collections.emptyList();
    }
}
