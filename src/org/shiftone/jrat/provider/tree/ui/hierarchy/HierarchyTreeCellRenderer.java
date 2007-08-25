package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.ClassHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.MethodHierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;
import org.shiftone.jrat.ui.util.DotIcon;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


public class HierarchyTreeCellRenderer extends DefaultTreeCellRenderer {

    private static Icon ICON_ROOT = new DotIcon(8, Color.darkGray);
    private static ClassLoader LOADER = HierarchyTreeCellRenderer.class.getClassLoader();
    private static Icon PACKAGE = new ImageIcon(LOADER.getResource("icons/package_obj.png"));
    private static Icon CLASS = new ImageIcon(LOADER.getResource("icons/class_obj.png"));
    private static Icon METHOD = new ImageIcon(LOADER.getResource("icons/methpub_obj.png"));

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
