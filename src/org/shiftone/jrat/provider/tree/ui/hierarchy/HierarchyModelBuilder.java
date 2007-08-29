package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;
import org.shiftone.jrat.provider.tree.ui.summary.MethodSummary;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.ClassHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodSummaryHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HierarchyModelBuilder {

    private final Map methodKeyNodes = new HashMap(); // method node cache <MethodKey, MethodSummaryHierarchyNode>
    private final Map packageNodes = new HashMap();  // package node cache <String, PackageHierarchyNode>

    private final PackageHierarchyNode root = new PackageHierarchyNode("");

    public HierarchyModelBuilder(MethodSummaryModel methodSummaryModel, Set allMethodKeys) {

        // getPreferences stack "performance" data into hierarchy
        for (Iterator i = methodSummaryModel.getMethodSummaryList().iterator(); i.hasNext();) {
            addMethodSummary((MethodSummary) i.next());
        }

        // loop over the "all methods" set to initially populate the hierarchy
        for (Iterator i = allMethodKeys.iterator(); i.hasNext();) {
            addMethod((MethodKey) i.next());
        }

        // update coverage counts
        root.finalizeStatistics();

    }

    public HierarchyTreeTableModel getModel() {
        return new HierarchyTreeTableModel(getRoot());
    }

    public PackageHierarchyNode getRoot() {
        return root;
    }

    private void addMethod(MethodKey methodKey) {
        if (!methodKeyNodes.containsKey(methodKey)) {
            // if the method does not exist in the map yet, then we are looking at an
            // non-covered method, and we need to add the MethodHierarchyNode.
            getClassNode(methodKey).addMethod(new MethodHierarchyNode(methodKey));
        }
    }


    /**
     * Gets a method hirarchy node based on a methodKey, and creates one.
     */
    private void addMethodSummary(MethodSummary methodSummary) {
        MethodKey methodKey = methodSummary.getMethodKey();
        ClassHierarchyNode classNode = getClassNode(methodKey);
        MethodHierarchyNode node = new MethodSummaryHierarchyNode(methodSummary);
        classNode.addMethod(node);
        methodKeyNodes.put(methodKey, node);

    }


    private ClassHierarchyNode getClassNode(MethodKey methodKey) {
        // todo - think about caching at this level also
        return getPackageNode(methodKey).getChildClass(methodKey.getClassName());
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