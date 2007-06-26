package org.shiftone.jrat.provider.tree.ui.graph;

import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.core.MethodKey;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

/**
 * @Author Jeff Drost
 */
public class GraphBuilder {

     public Map buildGraph(StackNode node) {
         Map map = new HashMap();
         buildGraph(node, map);
         return map;
    }

    public void buildGraph(StackNode node, Map nodes) {
        List children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            StackNode child = (StackNode) children.get(i);
            processNode(child, nodes);
            buildGraph(child, nodes);
        }
    }

    private void processNode(StackNode stackNode, Map nodes) {
        MethodKey methodKey = stackNode.getMethodKey();
        GraphNode graphNode = getGraphNode(methodKey, nodes);

        List children = stackNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            StackNode child = (StackNode) children.get(i);
            GraphNode childGraphNode = getGraphNode(child.getMethodKey(), nodes);

            graphNode.addCalled(childGraphNode);
            childGraphNode.addCalledBy(graphNode);
        }

        graphNode.addStackNode(stackNode);
    }

    private GraphNode getGraphNode(MethodKey methodKey, Map nodes) {
        GraphNode node = (GraphNode) nodes.get(methodKey);
        if (node == null) {
            node = new GraphNode(methodKey);
            nodes.put(methodKey, node);
        }
        return node;
    }


}
