package org.shiftone.jrat.provider.tree.ui.hierarchy.nodes;

import org.shiftone.jrat.provider.tree.ui.summary.MethodSummaryModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class PackageHierarchyNode extends HierarchyNode {


    private List childPackages = new ArrayList();
    private List childClasses = new ArrayList();
    private int totalMethods;
    private int executedMethods;
    private long totalDuration;
    private long totalMethodDuration;
    private long totalErrors;
    private long totalExits;

    public PackageHierarchyNode(String name, MethodSummaryModel methodSummaryModel) {
        super(name, methodSummaryModel);

    }

    public void finalizeStatistics() {

        for (Iterator i = childPackages.iterator(); i.hasNext();) {
            PackageHierarchyNode node = (PackageHierarchyNode) i.next();
            addStatistics(node);
        }
        for (Iterator i = childClasses.iterator(); i.hasNext();) {
            ClassHierarchyNode node = (ClassHierarchyNode) i.next();
            addStatistics(node);
        }

    }

    private void addStatistics(HierarchyNode node) {

        node.finalizeStatistics();

        totalMethods += node.getTotalMethods();
        executedMethods += node.getExecutedMethods();
        totalDuration += node.getTotalDuration();
        totalErrors += node.getTotalErrors();
        totalExits += node.getTotalExits();

        Long tmd = node.getTotalMethodDuration();
        if (tmd != null) {
            totalMethodDuration += tmd.longValue();
        }
    }


    public Long getTotalMethodDuration() {
        return new Long(totalMethodDuration);
    }

    public int getTotalMethods() {
        return totalMethods;
    }

    public int getExecutedMethods() {
        return executedMethods;
    }

    public long getTotalDuration() {
        return totalDuration;
    }


    public long getTotalExits() {
        return totalExits;
    }

    public long getTotalErrors() {
        return totalErrors;
    }

    public List getChildren() {
        List list = new ArrayList();
        list.addAll(childPackages);
        list.addAll(childClasses);
        return list;
    }

    public void addClass(ClassHierarchyNode classNode) {
        childClasses.add(classNode);
    }


    public void addPackage(PackageHierarchyNode packageNode) {
        childPackages.add(packageNode);
    }

    /**
     * Retreves the child package with the provided name or creates it if it
     * does not exist.
     */
    public PackageHierarchyNode getChildPackage(String name) {
        for (int i = 0; i < childPackages.size(); i++) {
            PackageHierarchyNode child = (PackageHierarchyNode) childPackages.get(i);
            if (child.getName().equals(name)) {
                return child;
            }
        }
        PackageHierarchyNode node = new PackageHierarchyNode(name, getMethodSummaryModel());
        addPackage(node);
        return node;
    }

    public PackageHierarchyNode getChildPackage(String[] nameParts) {
        if (nameParts.length == 0) {
            return this;
        }

        PackageHierarchyNode current = getChildPackage(nameParts[0]);
        for (int i = 1; i < nameParts.length; i++) {
            current = current.getChildPackage(nameParts[i]);
        }
        return current;
    }


    /**
     * Retreves the child class with the provided name or creates it if it does
     * not exist.
     */
    public ClassHierarchyNode getChildClass(String name) {
        for (int i = 0; i < childClasses.size(); i++) {
            ClassHierarchyNode child = (ClassHierarchyNode) childClasses.get(i);
            if (child.getName().equals(name)) {
                return child;
            }
        }
        ClassHierarchyNode node = new ClassHierarchyNode(name, getMethodSummaryModel());
        addClass(node);
        return node;
    }

}
