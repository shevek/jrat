package org.shiftone.jrat.provider.tree;


import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.util.log.Logger;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Class TreeNode
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TreeNode implements Externalizable {

    private static final Logger LOG = Logger.getLogger(TreeNode.class);
    private static final long serialVersionUID = 1;
    protected MethodKey methodKey;
    protected TreeNode parent;
    private Accumulator accumulator;
    protected HashMap children = new HashMap(5);


    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeObject(accumulator);
        out.writeObject(methodKey);

        // column a copy of the children
        List list = getChildren();

        // write a child count
        int childCount = list.size();
        out.writeInt(childCount);

        // write the children
        for (int i = 0; i < childCount; i++) {
            TreeNode child = (TreeNode) list.get(i);
            child.writeExternal(out);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        this.accumulator = (Accumulator) in.readObject();
        this.methodKey = (MethodKey) in.readObject();

        int childCount = in.readInt();
        for (int i = 0; i < childCount; i++) {

            TreeNode child = new TreeNode();

            child.readExternal(in);

            children.put(child.getMethodKey(), child);
            child.parent = this;
        }

    }

    public TreeNode() {

        // root node
        this.methodKey = null;
        this.parent = null;
        this.accumulator = new Accumulator();
    }


    public TreeNode(MethodKey methodKey, TreeNode treeNode) {
        this.methodKey = methodKey;
        this.parent = treeNode;
        this.accumulator = new Accumulator();
    }

    public List getChildren() {

        List list = new ArrayList();

        synchronized (children) {
            list.addAll(children.values());
        }

        return list;
    }


    /**
     * Method gets <b>AND CREATES IF NEEDED</b> the requested tree node
     */
    public TreeNode getChild(MethodKey methodKey) {

        TreeNode treeNode = null;

        synchronized (children) {
            treeNode = (TreeNode) children.get(methodKey);

            if (treeNode == null) {
                treeNode = new TreeNode(methodKey, this);

                children.put(methodKey, treeNode);
            }
        }

        return treeNode;
    }


    public final TreeNode getParentNode() {
        return parent;
    }


    public final boolean isRootNode() {
        return (methodKey == null);
    }


    public Accumulator getAccumulator() {
        return accumulator;
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }

    // ---------------------------------------------------------------

    public synchronized void reset() {

        // need to clone map - concurrency issues
        List list = new ArrayList();

        synchronized (children) {
            list.addAll(children.values());
        }

        for (int i = 0; i < list.size(); i++) {
            TreeNode treeNode = (TreeNode) list.get(i);

            treeNode.reset();
        }

        accumulator.reset();  // this is the actual call to reset
    }


}
