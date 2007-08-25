package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.ClassHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HierarchyViewBuilder {

    private final Map methodKeyNodes = new HashMap(); // method node cache <MethodKey, MethodHierarchyNode>
    private final Map packageNodes = new HashMap();  // package node cache <String, PackageHierarchyNode>

    private final PackageHierarchyNode root = new PackageHierarchyNode("");

    public HierarchyViewBuilder(StackNode rootNode, Set allMethodKeys) {

        // loop over the "all methods" set to initially populate the hierarchy
        Iterator iterator = allMethodKeys.iterator();
        while (iterator.hasNext()) {
            getMethodNode((MethodKey) iterator.next());
        }

        // load stack "performance" data into hierarchy
        initializeStats(rootNode);

        // update coverage counts
        root.finalizeStatistics();

    }

    public HierarchyTreeTableModel getModel() {
        return new HierarchyTreeTableModel(getRoot());
    }

    public PackageHierarchyNode getRoot() {
        return root;
    }

    /**
     * recurse over all the stats data and populate it into the hirerchy nodes.
     */
    private void initializeStats(StackNode input) {

        if (!input.isRootNode()) {
            MethodKey methodKey = input.getMethodKey();
            MethodHierarchyNode methodNode = getMethodNode(methodKey);
            methodNode.addStatistics(input);
        }

        for (Iterator iterator = input.getChildren().iterator(); iterator.hasNext();) {
            initializeStats((StackNode) iterator.next());
        }
    }


    /**
     * Gets a method hirarchy node based on a methodKey, and creates one if it doesn't exist yet.
     */
    private MethodHierarchyNode getMethodNode(MethodKey methodKey) {
        MethodHierarchyNode node = (MethodHierarchyNode) methodKeyNodes.get(methodKey);
        if (node == null) {
            ClassHierarchyNode classNode = getClassNode(methodKey);
            MethodHierarchyNode methodNode = new MethodHierarchyNode(methodKey);
            classNode.addMethod(methodNode);
            methodKeyNodes.put(methodKey, methodNode);
        }
        return node;
    }


    private ClassHierarchyNode getClassNode(MethodKey methodKey) {
        // todo - think about caching at this level also
        return getPackageNode(methodKey).getChildClass(methodKey.getShortClassName());
    }


    private PackageHierarchyNode getPackageNode(MethodKey methodKey) {
        PackageHierarchyNode node = (PackageHierarchyNode) packageNodes.get(methodKey.getPackageName());
        if (node == null) {
            node = root.getChildPackage(methodKey.getPackageNameParts());
            packageNodes.put(methodKey.getPackageName(), node);
        }
        return node;
    }

}