package org.shiftone.jrat.provider.tree.ui.graph;

import java.util.ArrayList;
import java.util.List;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.TreeNode;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class GraphNode {

    private final MethodKey methodKey;
    private final List stackNodes = new ArrayList();
    private final List called = new ArrayList();
    private final List calledBy = new ArrayList();

    public GraphNode(MethodKey methodKey) {
        this.methodKey = methodKey;
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }

    public void addStackNode(TreeNode treeNode) {
        stackNodes.add(treeNode);
    }

    public void addCalled(GraphNode graphNode) {
        called.add(graphNode);
    }

    public void addCalledBy(GraphNode graphNode) {
        calledBy.add(graphNode);
    }
}
