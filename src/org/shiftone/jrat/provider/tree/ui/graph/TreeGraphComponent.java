package org.shiftone.jrat.provider.tree.ui.graph;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.PercentColorLookup;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.Scrollable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;


/**
 * @author Jeff Drost
 */
public class TreeGraphComponent extends BufferedJComponent implements Scrollable {

    private static final Logger LOG = Logger.getLogger(TreeGraphComponent.class);
    private StackTreeNode node;
    private Color LINE_COLOR = Color.LIGHT_GRAY;
    private PercentColorLookup colorLookup = new PercentColorLookup();
    private DecimalFormat pctDecimalFormat = new DecimalFormat("#,###.#'%'");
    private Font font = new Font("SansSerif", Font.PLAIN, 9);
    private int rowHeight = 12;

    public TreeGraphComponent() {
        setDoubleBuffered(false);
        setBackground(Color.WHITE);
    }


    protected void paintBuffer(Graphics2D g) {

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        if ((node != null) && !node.isRootNode() && (node.getTotalExits() != 0)) {
            paint(g2d, node, 0, 0, getWidth());
        }
    }


    /**
     * @todo clean up this ugly code
     */
    private void paint(Graphics2D g, StackTreeNode node, int x, int row, int width) {

        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics();
        Color color = colorLookup.getColor(node.getPctOfAvgParentDuration());
        int height = metrics.getHeight() + metrics.getDescent();
        int y = row * height;

        rowHeight = height;

        g.setColor(color);
        g.fill3DRect(x, y, width, height, true);

        // print the text on the node
        Graphics gg = g.create(x, y, width, height);

        gg.setColor(Color.BLACK);

        MethodKey methodKey = node.getMethodKey();
        String text = methodKey.getClassName() + "." + methodKey.getMethodName() + " "
                + pctDecimalFormat.format(node.getPctOfAvgRootDuration());
        Rectangle2D stringBounds = metrics.getStringBounds(text, g);

        if (stringBounds.getWidth() < width) {
            gg.drawString(text, (int) (width / 2 - stringBounds.getWidth() / 2), (int) (stringBounds.getHeight()));
        } else {
            text = methodKey.getMethodName() + " " + pctDecimalFormat.format(node.getPctOfAvgRootDuration());
            stringBounds = metrics.getStringBounds(text, g);

            if (stringBounds.getWidth() < width) {
                gg.drawString(text, (int) (width / 2 - stringBounds.getWidth() / 2), (int) (stringBounds.getHeight()));
            }
        }

        // print the children
        long total = node.getTotalDurationNanos();

        if ((total > 0) && (node.getChildCount() > 0)) {
            int childX = 0;

            for (int i = 0; i < node.getChildCount(); i++) {
                StackTreeNode child = (StackTreeNode) node.getChildAt(i);
                long part = child.getTotalDurationNanos();
                int partWidth = (int) ((part * (long) width) / total);

                if (partWidth > 1) {
                    paint(g, child, x + childX, row + 1, partWidth);
                } else {
                    g.setColor(LINE_COLOR);
                    g.drawLine(x + childX, (row + 1) * height, x + childX, (row + 1 + node.getMaxDepth()) * height);
                }

                childX += partWidth;
            }
        }
    }


    public synchronized void setStackTreeNode(StackTreeNode node) {

        //LOG.info("setStackTreeNode " + node + " " + node.getMaxDepth());
        this.node = node;

        dataChanged();
        setPreferredSize(new Dimension(getWidth(), (int) (rowHeight * node.getMaxDepth())));
        setSize(getPreferredSize());
        // LOG.info("getPreferredSize " + getPreferredSize());
        if (isVisible()) {
            repaint();
        }
    }


    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }


    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return (int) rowHeight;
    }


    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return (int) rowHeight;
    }


    public boolean getScrollableTracksViewportWidth() {
        return true;
    }


    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
