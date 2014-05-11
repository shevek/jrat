package org.shiftone.jrat.provider.tree.ui.hierarchy;

import java.util.List;
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
    public static final Table.Column TOTAL_EXITS = TABLE.column("Exists", false);
    public static final Table.Column UNCALLED = TABLE.column("Uncalled", false);
    public static final Table.Column COVERAGE = TABLE.column("Coverage %", false);
    public static final Table.Column EXCEPTIONS = TABLE.column("Exceptions", false);
    public static final Table.Column ERROR_RATE = TABLE.column("Error Rate", false);
    public static final Table.Column TOTAL = TABLE.column("Total ms", false);
    public static final Table.Column TOTAL_METHOD = TABLE.column("Total Method ms");
    public static final Table.Column PERCENT_METHOD = TABLE.column("Method Time %");

    public static List getColumns() {
        return TABLE.getColumns();
    }

    private static Integer getTotalMethods(HierarchyNode node) {
        return (node instanceof MethodHierarchyNode) ? null : node.getTotalMethods();
    }

    private static Integer getUncalledMethods(HierarchyNode node) {
        return (node instanceof MethodHierarchyNode) ? null : node.getUncalledMethods();
    }

    private final PackageHierarchyNode root;

    public HierarchyTreeTableModel(PackageHierarchyNode root) {
        this.root = root;
    }

    @Override
    public Class getColumnClass(int i) {
        return TABLE.getColumn(i).getType();
    }

    @Override
    public int getColumnCount() {
        return TABLE.getColumnCount();
    }

    @Override
    public String getColumnName(int i) {
        return TABLE.getColumn(i).getName();
    }

    @Override
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
            return node.getTotalDuration();
        }
        if (TOTAL_METHOD.getIndex() == i) {
            return node.getTotalMethodDuration();
        }
        if (PERCENT_METHOD.getIndex() == i) {
            return node.getTotalMethodPercent();
        }
        if (TOTAL_EXITS.getIndex() == i) {
            return node.getTotalExits();
        }
        if (EXCEPTIONS.getIndex() == i) {
            return node.getTotalErrors();
        }
        if (ERROR_RATE.getIndex() == i) {
            return node.getErrorRate();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(Object o, int i) {
        return false;
    }

    @Override
    public void setValueAt(Object o, Object o1, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return node(parent).getChild(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return node(parent).getChildCount();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return node(parent).getIndexOfChild((HierarchyNode) child);
    }

    @Override
    public boolean isLeaf(Object o) {
        return node(o).isLeaf();
    }

    private HierarchyNode node(Object o) {
        return (HierarchyNode) o;
    }

}
