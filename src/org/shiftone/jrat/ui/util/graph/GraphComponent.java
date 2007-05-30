package org.shiftone.jrat.ui.util.graph;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;


/**
 * Class GraphComponent
 *
 * @author Jeff Drost
 */
public class GraphComponent extends JComponent implements Scrollable, ChangeListener {

    private static final Logger LOG = Logger.getLogger(GraphComponent.class);
    private static DecimalFormat LONG_FORMAT = new DecimalFormat("###,###,###,###,###");
    private static final Color GRID_LIGHT_COLOR = new Color(220, 220, 220);
    private static final Color GRID_DARK_COLOR = new Color(180, 180, 180);
    private static final Font KEY_FONT = new Font("Monospaced", Font.PLAIN, 11);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final int HEADER_PIX = 20;
    private static final int FOOTER_PIX = 5;
    private static final int ABOVE_LINE_PIX = 1;
    private static final int KEY_INDENT_PIX = 3;
    private static final int TITLE_INDENT_PIX = 100;
    private static final int TITLE_HEADER_PIX = 12;
    private static final int DOT_RADIUS_PIX = 2;
    private static final int GAP_THRESH_DOTS = 7;
    private static final int GAP_THRESH_MINOR_LINES = 10;
    private static final int GAP_THRESH_MAJOR_LINES = 4;
    private JViewport viewport = null;
    private int pointGap = 20;
    private GraphModelSet graphModelSet = null;

    /**
     * Constructor GraphComponent
     */
    public GraphComponent() {
        this(new GraphModelSet());
    }


    /**
     * Constructor GraphComponent
     *
     * @param graphModelSet
     */
    public GraphComponent(GraphModelSet graphModelSet) {

        this.graphModelSet = graphModelSet;

        graphModelSet.addChangeListener(this);
    }


    /**
     * Method getPointGap
     */
    public int getPointGap() {
        return pointGap;
    }


    /**
     * Method setPointGap
     */
    public void setPointGap(int pointGap) {

        if (this.pointGap != pointGap) {
            this.pointGap = pointGap;

            setSize(getPreferredSize());
        }
    }


    /**
     * Method stateChanged
     */
    public void stateChanged(ChangeEvent e) {
        repaint();
    }


    /**
     * Method getGraphModelSet
     */
    public GraphModelSet getGraphModelSet() {
        return graphModelSet;
    }


    /**
     * Method setGraphModelSet
     */
    public void setGraphModelSet(GraphModelSet graphModelSet) {
        this.graphModelSet = graphModelSet;
    }


    /**
     * Method paintGraph
     */
    public void paintGraph(Graphics g, GraphModel graph, double ratio, long minValue, int firstPoint, int lastPoint) {

        Color lineColor = graph.getColor();
        Color dotColor = lineColor.darker();

        for (int i = firstPoint + 1; i < lastPoint; i++) {
            int px = ((i - 1) * pointGap);
            int tx = ((i + 0) * pointGap);
            long pValue = graph.getValue(i - 1) - minValue;
            long tValue = graph.getValue(i - 0) - minValue;
            int py = (int) ((double) pValue * ratio);
            int ty = (int) ((double) tValue * ratio);

            g.setColor(lineColor);
            g.drawLine(px, getHeight() - FOOTER_PIX - py, tx, getHeight() - FOOTER_PIX - ty);

            if (pointGap > GAP_THRESH_DOTS) {
                g.setColor(dotColor);
                g.fillRect(tx - DOT_RADIUS_PIX, getHeight() - FOOTER_PIX - ty - DOT_RADIUS_PIX, DOT_RADIUS_PIX * 2,
                        DOT_RADIUS_PIX * 2);
            }
        }
    }


    /**
     * Method paintGraphSet
     */
    private void paintGraphSet(Graphics g, GraphModelSet graphSet, Rectangle viewRect, int firstPoint, int lastPoint) {

        int fontHeight = g.getFontMetrics(KEY_FONT).getHeight();
        int height = getHeight();
        int viewRectWidth = (int) viewRect.getWidth();
        int x = (int) viewRect.getX();
        int minY = getHeight() - FOOTER_PIX;
        int keys = height / fontHeight;
        long maxValue = graphSet.getMaxValue();
        long minValue = graphSet.getMinValue();
        long valueDelta = maxValue - minValue;
        long[] keyValues = null;

        g.setFont(KEY_FONT);
        paintVerticleLines(firstPoint, lastPoint, g);

        keys = Math.max(keys, 1);
        keyValues = calcKeyValues(minValue, keys, maxValue, valueDelta);

        if ((maxValue > 0) && (valueDelta > 0)) {
            double ratio = (double) (getHeight() - HEADER_PIX - FOOTER_PIX) / valueDelta;

            // draw horizontal lines
            for (int i = 0; i < keyValues.length; i++) {
                long value = keyValues[i];
                int y = minY - (int) (ratio * (value - minValue));

                g.setColor(GRID_LIGHT_COLOR);
                g.drawLine(x, y, x + viewRectWidth, y);
            }

            // draw the graphs
            for (int i = 0; i < graphSet.getGraphModelCount(); i++) {
                paintGraph(g, graphSet.getGraphModel(i), ratio, minValue, firstPoint, lastPoint);
            }

            // draw key on left side of component
            for (int i = 0; i < keyValues.length; i++) {
                long value = keyValues[i];
                int y = minY - (int) (ratio * (value - minValue));

                g.setColor(Color.black);
                g.setXORMode(Color.white);
                g.drawString(toString(value), x + KEY_INDENT_PIX, y - ABOVE_LINE_PIX);
            }
        }

        if (graphSet.getTitle() != null) {
            g.setColor(Color.black);
            g.setFont(TITLE_FONT);
            g.drawString(graphSet.getTitle(), x + TITLE_INDENT_PIX, TITLE_HEADER_PIX);
        }
    }


    /**
     * Method calcKeyValues
     */
    private long[] calcKeyValues(long minValue, int keys, long maxValue, long valueDelta) {

        long[] keyValues = new long[keys];

        try {
            keyValues[0] = minValue;
            keyValues[keyValues.length - 1] = maxValue;

            for (int i = 1; i < keyValues.length - 1; i++) {
                keyValues[i] = minValue + (int) ((double) (valueDelta * i) / (double) (keyValues.length - 1));
            }
        }
        catch (Exception e) {
            LOG.info("calcKeyValues " + keys, e);
        }

        return keyValues;
    }


    /**
     * Method paintVerticleLines
     */
    private void paintVerticleLines(int firstPoint, int lastPoint, Graphics g) {

        g.setColor(GRID_LIGHT_COLOR);

        for (int i = firstPoint + 1; i < lastPoint; i++) {
            if (i % 10 == 0) {
                if ((pointGap > GAP_THRESH_MAJOR_LINES) || (i % 100 == 0)) {
                    g.setColor(GRID_DARK_COLOR);
                    g.drawLine(pointGap * i, 0, pointGap * i, getHeight());
                    g.drawString(toString(i), (pointGap * i) + 2, TITLE_HEADER_PIX);
                    g.setColor(GRID_LIGHT_COLOR);
                }
            } else if (pointGap > GAP_THRESH_MINOR_LINES) {
                g.drawLine(pointGap * i, 0, pointGap * i, getHeight());
            }
        }
    }


    /**
     * Method toString
     */
    private static synchronized String toString(long l) {
        return LONG_FORMAT.format(l);
    }


    /**
     * Method paint
     */
    public void paintComponent(Graphics g) {

        Rectangle viewRect = (viewport == null)
                ? getBounds()
                : viewport.getViewRect();
        int dataPoints = getDataPointCount();
        int firstPoint = (int) (viewRect.getX() / pointGap);
        int lastPoint = (int) (viewRect.getWidth() / pointGap) + firstPoint;

        lastPoint = Math.min(lastPoint + 2, dataPoints);

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        paintGraphSet(g, graphModelSet, viewRect, firstPoint, lastPoint);
    }


    /**
     * Method getDataPointCount
     */
    private int getDataPointCount() {
        return graphModelSet.getMaxPointCount();
    }


    /**
     * Method addNotify
     */
    public void addNotify() {

        Container container = getParent();

        super.addNotify();
        LOG.info("addNotify");

        while (container != null) {
            LOG.info("getParent() = " + container);

            if (container instanceof JViewport) {
                LOG.info("container instanceof JViewport");

                this.viewport = (JViewport) container;

                break;
            }

            container = container.getParent();
        }
    }


    /**
     * Method getPreferredScrollableViewportSize
     */
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();    // new Dimension(200, 200);
    }


    /**
     * Method getScrollableBlockIncrement
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return pointGap;
    }


    /**
     * Method getScrollableTracksViewportHeight
     */
    public boolean getScrollableTracksViewportHeight() {
        return true;
    }


    /**
     * Method getScrollableTracksViewportWidth
     */
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }


    /**
     * Method getScrollableUnitIncrement
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return pointGap;
    }


    /**
     * Method getPreferredSize
     */
    public Dimension getPreferredSize() {
        return new Dimension((getDataPointCount() - 1) * pointGap, getHeight());
    }
}
