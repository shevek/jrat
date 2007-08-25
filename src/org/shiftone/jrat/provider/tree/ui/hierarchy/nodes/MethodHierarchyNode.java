package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;

import java.util.List;
import java.util.Collections;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodHierarchyNode extends HierarchyNode {

    private final MethodKey methodKey;
    private long totalEnters;
    private long totalExits;

    public MethodHierarchyNode(MethodKey methodKey) {
        super(methodKey.getMethodName() + "(" + methodKey.getSig().getShortText() + ")");
        this.methodKey = methodKey;
    }


    public void addStatistics(StackNode input) {
        Accumulator accumulator = input.getAccumulator();
        totalEnters += accumulator.getTotalEnters();
        totalExits += accumulator.getTotalExits();
    }

    public long getTotalEnters() {
        return totalEnters;
    }

    public long getTotalExits() {
        return totalExits;
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
        return totalEnters > 0 ? 1 : 0;
    }

    public int getExistedMethods() {
        return totalExits > 0 ? 1 : 0;
    }


  public List getChildren() {
    return Collections.emptyList();
  }
}
