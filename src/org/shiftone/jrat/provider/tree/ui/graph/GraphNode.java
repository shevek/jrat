package org.shiftone.jrat.provider.tree.ui.graph;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;

import java.util.List;
import java.util.ArrayList;

/**
 * @Author Jeff Drost
 */
public class GraphNode {

    private MethodKey methodKey;
    private List stackNodes = new ArrayList();
    private List called = new ArrayList();
    private List calledBy = new ArrayList();


    public GraphNode(MethodKey methodKey) {
        this.methodKey = methodKey;
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }

    public void addStackNode(StackNode stackNode) {
         stackNodes.add(stackNode);
    }

    public void addCalled(GraphNode graphNode) {
         called.add(graphNode);
    }

    public void addCalledBy(GraphNode graphNode) {
         calledBy.add(graphNode);
    }
}
