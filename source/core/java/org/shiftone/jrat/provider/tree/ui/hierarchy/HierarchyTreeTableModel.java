package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.shiftone.jrat.desktop.util.Table;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.HierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class HierarchyTreeTableModel extends AbstractTreeTableModel {

    public static final Table TABLE = new Table();
    public static final Table.Column CLASS = TABLE.column("Class");
    public static final Table.Column METHODS = TABLE.column("Methods");
    public static final Table.Column UNCALLED = TABLE.column("Uncalled");
    public static final Table.Column COVERAGE = TABLE.column("Coverage %");
    public static final Table.Column TOTAL = TABLE.column("Total ms");
    public static final Table.Column TOTAL_METHOD = TABLE.column("Total Method ms");
    public static final Table.Column PERCENT_METHOD = TABLE.column("Method Time %");
    public static final Table.Column TOTAL_EXITS = TABLE.column("Exists");
    public static final Table.Column EXCEPTIONS = TABLE.column("Exceptions");
    public static final Table.Column ERROR_RATE = TABLE.column("Error Rate");

    private final PackageHierarchyNode root;

    public HierarchyTreeTableModel(PackageHierarchyNode root) {
        this.root = root;
    }

    public Class getColumnClass(int i) {
        return TABLE.getColumn(i).getType();
    }

    public int getColumnCount() {
        return TABLE.getColumnCount();
    }

    public String getColumnName(int i) {
        return TABLE.getColumn(i).getName();
    }

    public Object getValueAt(Object o, int i) {

        HierarchyNode node = (HierarchyNode) o;

        if (CLASS.getIndex() == i) {
            return o.toString();
        }
        if (METHODS.getIndex() == i) {
            return getTotalMethods(node);
        }
        if (UNCALLED.getIndex() == i) {
            return getUncalledMethods(node);
        }
        if (COVERAGE.getIndex() == i) {
            return node.getCoverage();
        }
        if (TOTAL.getIndex() == i) {
            return new Long(node.getTotalDuration());
        }
        if (TOTAL_METHOD.getIndex() == i) {
            return node.getTotalMethodDuration();
        }
        if (PERCENT_METHOD.getIndex() == i) {
            return node.getTotalMethodPercent();
        }
        if (TOTAL_EXITS.getIndex() == i) {
            return new Long(node.getTotalExits());
        }
        if (EXCEPTIONS.getIndex() == i) {
            return new Long(node.getTotalErrors());
        }
        if (ERROR_RATE.getIndex() == i) {
            return node.getErrorRate();
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
