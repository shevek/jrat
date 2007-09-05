package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.HierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;
import org.shiftone.jrat.util.Percent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class HierarchyTreeTableModel extends AbstractTreeTableModel {

    private static final String[] COLUMNS = {
            "Class", "Methods", "Uncalled", "Coverage %", "Total ms", "Total Method ms"
    };
    private static final Class[] COLUMN_TYPES = {
            String.class, Integer.class, Integer.class,  Percent.class, Long.class, Long.class
    };

    private final PackageHierarchyNode root;

    public HierarchyTreeTableModel(PackageHierarchyNode root) {
        this.root = root;
    }

    public Class getColumnClass(int i) {
        return COLUMN_TYPES[i];
    }

    public int getColumnCount() {
        return COLUMNS.length;
    }

    public String getColumnName(int i) {
        return COLUMNS[i];
    }

    public Object getValueAt(Object o, int i) {
        HierarchyNode node = (HierarchyNode)o;
        switch (i) {
            case 0:
                return o.toString();
            case 1:
                return getTotalMethods(node);
            case 2:
                return getUncalledMethods(node);
            case 3:
                return node.getCoverage();
            case 4:
                return new Long(node.getTotalDuration());
            case 5:
                return node.getTotalMethodDuration();

        }
        return null;
    }

    private static Integer getTotalMethods(HierarchyNode node) {
       return (node instanceof MethodHierarchyNode) ? null : new Integer(node.getTotalMethods());
    }

    private static Integer getUncalledMethods(HierarchyNode node) {
       return (node instanceof MethodHierarchyNode) ? null : new Integer(node.getUncalledMethods());
    }

    public boolean isCellEditable(Object o, int i) {
        return false;
    }

    public void setValueAt(Object o, Object o1, int i) {
        throw new UnsupportedOperationException();
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        return node(parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        return node(parent).getChildCount();
    }

    public int getIndexOfChild(Object parent, Object child) {
        return node(parent).getIndexOfChild((HierarchyNode) child);
    }

    public boolean isLeaf(Object o) {
        return node(o).isLeaf();
    }

    private HierarchyNode node(Object o) {
        return (HierarchyNode) o;
    }
}
