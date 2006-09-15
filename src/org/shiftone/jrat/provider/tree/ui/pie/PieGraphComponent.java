package org.shiftone.jrat.provider.tree.ui.pie;



import org.shiftone.jrat.provider.tree.ui.PercentColorLookup;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.provider.tree.ui.graph.BufferedJComponent;
import org.shiftone.jrat.util.log.Logger;

import java.awt.Graphics2D;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class PieGraphComponent extends BufferedJComponent {

    private static final Logger LOG = Logger.getLogger(PieGraphComponent.class);
    private StackTreeNode       root;
    private PercentColorLookup  colorLookup = new PercentColorLookup();

    public synchronized void setStackTreeNode(StackTreeNode root) {

        this.root = root;

        dataChanged();

        if (isVisible())
        {
            repaint();
        }
    }


    protected void paintBuffer(Graphics2D g) {

        double totalRadius = Math.min(getWidth(), getHeight()) / 2;
        int    x           = getWidth() / 2;
        int    y           = getHeight() / 2;

        // int maxDepth = getMaxEffectiveDepth(root) ;
        int    maxDepth    = 10;
        double radiusDelta = totalRadius / maxDepth;

        paintNode(g, root, x, y, radiusDelta, 0, 360, 1, maxDepth);
    }


    public int getMaxEffectiveDepth(StackTreeNode node) {

        int maxChildDepth = 0;

        for (int i = 0; i < node.getChildCount(); i++)
        {
            StackTreeNode child = (StackTreeNode) node.getChildAt(i);

            if (child.getTotalDurationNanos() > 0)
            {
                maxChildDepth = Math.max(maxChildDepth, getMaxEffectiveDepth(child));
            }
        }

        return 1 + maxChildDepth;
    }


    public void paintNode(Graphics2D g, StackTreeNode node, int x, int y, double radiusDelta, int min, int max,
                          int depth, int maxDepth) {

        if ((node == null) || (depth > maxDepth))
        {
            return;
        }

        long totalDegrees = max - min;
        long totalNanos   = node.getTotalDurationNanos();

        if ((totalNanos > 0) && (node.getChildCount() > 0))
        {
            int startDegrees = min;

            for (int i = 0; i < node.getChildCount(); i++)
            {
                StackTreeNode child       = (StackTreeNode) node.getChildAt(i);
                long          partNanos   = child.getTotalDurationNanos();
                int           partDegrees = (int) ((partNanos * totalDegrees) / totalNanos);

                if (partDegrees > 1)
                {
                    paintNode(g, child, x, y, radiusDelta, startDegrees, startDegrees + partDegrees, depth + 1,
                              maxDepth);
                }

                startDegrees += partDegrees;
            }
        }

        int radius   = (int) (radiusDelta * depth);
        int diameter = radius * 2;

        g.setColor(colorLookup.getColor(node.getPctOfAvgParentDuration()));
        g.fillArc(x - radius, y - radius, diameter, diameter, min, max - min);
    }
}
