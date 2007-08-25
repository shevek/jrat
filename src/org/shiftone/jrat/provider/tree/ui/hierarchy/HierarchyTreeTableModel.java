package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.treetable.TreeTableModel;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.HierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;
import org.shiftone.jrat.util.Percent;

import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class HierarchyTreeTableModel extends AbstractTreeTableModel {

    private static final String[] COLUMNS = {
            "Class", "Methods", "Uncalled", "Coverage", "Duration"
    };
    private static final Class[] COLUMN_TYPES = {
            String.class, Integer.class, Integer.class, Percent.class, Long.class
    };

    private final PackageHierarchyNode root;

    public HierarchyTreeTableModel(PackageHierarchyNode root) {
        this.root = root;
    }

    public Class getColumnClass(int i) {
        return (i == 0) ? String.class : Integer.class;
    }

    public int getColumnCount() {
        return COLUMNS.length;
    }

    public String getColumnName(int i) {
        return COLUMNS[i];
    }


    public Object getValueAt(Object o, int i) {
        switch (i) {
            case 0:
                return o.toString();
            case 1:
                return new Integer(node(o).getTotalMethods());
            case 2:
                return new Integer(node(o).getUncalledMethods());
            case 3:
                return node(o).getCoverage();
            case 4:
                return new Long(node(o).getTotalDurationMs());
        }
        return null;
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

    public boolean isLeaf(Object node) {
        return (node instanceof MethodHierarchyNode);
    }

    private HierarchyNode node(Object o) {
        return (HierarchyNode) o;
    }


}
