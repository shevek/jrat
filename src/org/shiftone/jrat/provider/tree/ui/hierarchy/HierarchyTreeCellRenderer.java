package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.shiftone.jrat.desktop.util.Icons;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.ClassHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodSummaryHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class HierarchyTreeCellRenderer extends DefaultTreeCellRenderer {

    private static Icon PACKAGE = Icons.getIcon("package_obj.png");
    private static Icon CLASS = Icons.getIcon("class_obj.png");
    private static Icon METHOD = Icons.getIcon("methpub_obj.png");

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof PackageHierarchyNode) {

            setIcon(PACKAGE);

        } else if (value instanceof ClassHierarchyNode) {

            setIcon(CLASS);

        } else if (value instanceof MethodHierarchyNode) {            

            setIcon(METHOD);

        }

        return component;
    }
}
