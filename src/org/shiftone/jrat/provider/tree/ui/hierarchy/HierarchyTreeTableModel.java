// Copyright 2007 Google Inc. All Rights Reserved.

package org.shiftone.jrat.provider.tree.ui.hierarchy;

import org.jdesktop.swingx.treetable.TreeTableModel;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.HierarchyNode;
import org.shiftone.jrat.provider.tree.ui.hierarchy.nodes.PackageHierarchyNode;

import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;

/**
 * @author jeffdrost@google.com (Jeff Drost)
 */
public class HierarchyTreeTableModel extends AbstractTreeTableModel {

  private static final String[] COLUMNS = {
      "X", "A", "B", "C"
  };

  private final PackageHierarchyNode root;

  public HierarchyTreeTableModel(PackageHierarchyNode root) {
    this.root = root;
  }

  public Class getColumnClass(int i) {
    return Object.class;
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
        return new Integer(node(o).getEnteredMethods());
      case 3:
        return new Integer(node(o).getExistedMethods());
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

  private HierarchyNode node(Object o) {
    return (HierarchyNode) o;
  }

  public boolean isLeaf(Object node) {
    return node(node).isLeaf();
  }

  public void valueForPathChanged(TreePath path, Object newValue) {
    throw new UnsupportedOperationException();
  }


  public void addTreeModelListener(TreeModelListener l) {
    //throw new UnsupportedOperationException();
  }

  public void removeTreeModelListener(TreeModelListener l) {
    //throw new UnsupportedOperationException();
  }
}
