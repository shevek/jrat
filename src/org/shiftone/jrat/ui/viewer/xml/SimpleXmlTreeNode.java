package org.shiftone.jrat.ui.viewer.xml;


import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;


/**
 * Class SimpleXmlTreeNode
 *
 * @author Jeff Drost
 *
 */
public class SimpleXmlTreeNode implements TreeNode {

    private SimpleXmlTreeNode parent     = null;
    private String            name       = null;
    private Properties        properties = null;
    private List              childList  = new ArrayList();

    /**
     * Constructor SimpleXmlTreeNode
     *
     *
     * @param parent
     * @param name
     * @param properties
     */
    public SimpleXmlTreeNode(SimpleXmlTreeNode parent, String name, Properties properties) {

        this.parent     = parent;
        this.name       = name;
        this.properties = properties;
    }


    /**
     * Constructor SimpleXmlTreeNode
     *
     */
    public SimpleXmlTreeNode() {}


    /**
     * Method addChild
     */
    public void addChild(SimpleXmlTreeNode node) {
        childList.add(node);
    }


    /**
     * Method children
     */
    public Enumeration children() {
        return Collections.enumeration(childList);
    }


    /**
     * Method getAllowsChildren
     */
    public boolean getAllowsChildren() {
        return true;
    }


    /**
     * Method getChildAt
     */
    public TreeNode getChildAt(int childIndex) {
        return (TreeNode) childList.get(childIndex);
    }


    /**
     * Method getChildCount
     */
    public int getChildCount() {
        return childList.size();
    }


    /**
     * Method getIndex
     */
    public int getIndex(TreeNode node) {
        return childList.indexOf(node);
    }


    /**
     * Method getParent
     */
    public TreeNode getParent() {
        return parent;
    }


    /**
     * Method isLeaf
     */
    public boolean isLeaf() {
        return (getChildCount() == 0);
    }


    /**
     * Method toString
     */
    public String toString() {

        return (name == null)
               ? "DOCUMENT"
               : name + " " + properties;
    }
}
