package org.shiftone.jrat.provider.tree.ui.touch;


import com.touchgraph.graphlayout.GLPanel;
import com.touchgraph.graphlayout.Node;
import com.touchgraph.graphlayout.TGException;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.provider.tree.ui.PercentColorLookup;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.util.Assert;

import java.awt.Color;


/**
 * @author Jeff Drost
 *
 */
public class StackTreeNodeGLPanel extends GLPanel {

    private PercentColorLookup colorLookup = new PercentColorLookup();

    public StackTreeNodeGLPanel(StackTreeNode root) {

        super();

        Assert.assertNotNull("root", root);
        initialize(root);
    }


    public void initialize() {

        // this is here to prevent the parent class "initialize" from doing
        // anything.
    }


    public void initialize(StackTreeNode root) {

        buildPanel();
        buildLens();
        tgPanel.setLensSet(tgLensSet);
        addUIs();

        try
        {
            Node rootNode = buildTouchGraphTree(root, 0);

            tgPanel.setLocale(rootNode, 4);
            tgPanel.setSelect(rootNode);
            setLocalityRadius(4);

            // try {
            // Thread.currentThread().sleep(1000);
            // } catch (InterruptedException ex) {
            // }
            // getHVScroll().slowScrollToCenter(rootNode);
        }
        catch (TGException e)
        {
            throw new JRatException("buildTouchGraphTree failed ", e);
        }

        setVisible(true);
    }




    private Node buildTouchGraphTree(StackTreeNode treeNode, int depth) throws TGException {

        Assert.assertNotNull("treeNode", treeNode);

        MethodKey methodKey = treeNode.getMethodKey();
        String    title     = (methodKey == null)
                       ? "Root"
                       : methodKey.getMethodName();    // + "

        // - "
        // +
        // treeNode.getAverageDuration(TimeUnit.MS)
        // +
        // "ms";
        //
        Color color = colorLookup.getColor(treeNode.getPctOfAvgParentDuration());
        // this seems to be a problem - am I adding the same node more than once?
        String id = String.valueOf(Math.random() * 100000);
        Node  node  = new CallNode(id, title, treeNode, color);

        tgPanel.addNode(node);

        for (int i = 0; i < treeNode.getChildCount(); i++)
        {
            StackTreeNode child     = (StackTreeNode) treeNode.getChildAt(i);
            Color         edgeColor = colorLookup.getColor(child.getPctOfAvgParentDuration());
            Node          childNode = buildTouchGraphTree(child, depth + 1);
            CallEdge      edge      = new CallEdge(node, childNode, edgeColor);

            tgPanel.addEdge(edge);
        }

        return node;
    }
}
