package org.shiftone.jrat.provider.tree.ui.trace.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.Scrollable;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.provider.tree.ui.trace.PercentColorLookup;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TreeGraphComponent extends BufferedJComponent implements Scrollable {

    private static final Logger LOG = Logger.getLogger(TreeGraphComponent.class);
    private TraceTreeNode node;
    private final Color LINE_COLOR = Color.LIGHT_GRAY;
    private final PercentColorLookup colorLookup = new PercentColorLookup();
    private final DecimalFormat pctDecimalFormat = new DecimalFormat("#,###.#'%'");
    private final Font font = new Font("SansSerif", Font.PLAIN, 9);
    private int rowHeight = 12;

    public TreeGraphComponent() {
        setDoubleBuffered(false);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintBuffer(Graphics2D g) {

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        if ((node != null) && !node.isRootNode() && (node.getAccumulator().getTotalExits() != 0)) {
            paint(g, node, 0, 0, getWidth());
        }
    }

    /**
     * @todo clean up this ugly code
     */
    private void paint(Graphics2D g, TraceTreeNode node, int x, int row, int width) {

        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics();
        Color color = colorLookup.getColor(node.getPctOfMeanParentDuration());
        int height = metrics.getHeight() + metrics.getDescent();
        int y = row * height;

        rowHeight = height;

        g.setColor(color);
        g.fill3DRect(x, y, width, height, true);

        // print the text on the node
        Graphics gg = g.create(x, y, width, height);

        gg.setColor(Color.BLACK);

        MethodKey methodKey = node.getMethodKey();
        String text = methodKey.getClassName()
                + "." + methodKey.getMethodName()
                + " " + pctDecimalFormat.format(node.getPctOfMeanRootDuration());

        Rectangle2D stringBounds = metrics.getStringBounds(text, g);

        if (stringBounds.getWidth() < width) {
            gg.drawString(text, (int) (width / 2 - stringBounds.getWidth() / 2), (int) (stringBounds.getHeight()));
        } else {
            text = methodKey.getMethodName() + " " + pctDecimalFormat.format(node.getPctOfMeanRootDuration());
            stringBounds = metrics.getStringBounds(text, g);

            if (stringBounds.getWidth() < width) {
                gg.drawString(text, (int) (width / 2 - stringBounds.getWidth() / 2), (int) (stringBounds.getHeight()));
            }
        }

        // print the children
        long total = node.getAccumulator().getTotalDuration();

        if ((total > 0) && (node.getChildCount() > 0)) {
            int childX = 0;

            for (int i = 0; i < node.getChildCount(); i++) {
                TraceTreeNode child = node.getChildAt(i);
                long part = child.getAccumulator().getTotalDuration();
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

    public synchronized void setStackTreeNode(TraceTreeNode node) {

        //LOG.info("setStackTreeNode " + node + " " + node.getMaxDepth());
        this.node = node;

        dataChanged();
        setPreferredSize(new Dimension(getWidth(), rowHeight * node.getMaxDepth()));
        setSize(getPreferredSize());
        // LOG.info("getPreferredSize " + getPreferredSize());
        if (isVisible()) {
            repaint();
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return rowHeight;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return rowHeight;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
