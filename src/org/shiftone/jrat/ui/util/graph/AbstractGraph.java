package org.shiftone.jrat.ui.util.graph;


import org.shiftone.jrat.util.log.Logger;

import java.awt.Color;


/**
 * Class AbstractGraph
 *
 * @author Jeff Drost
 */
public abstract class AbstractGraph implements GraphModel {

    private static final Logger LOG = Logger.getLogger(AbstractGraph.class);
    private Long maxValue = null;
    private Long minValue = null;
    private Color color = Color.black;

    /**
     * Method getMaxValue
     */
    public long getMaxValue() {

        if (maxValue == null) {
            init();
        }

        return maxValue.longValue();
    }


    /**
     * Method getMinValue
     */
    public long getMinValue() {

        if (minValue == null) {
            init();
        }

        return minValue.longValue();
    }


    /**
     * Method init
     */
    public void init() {

        long max = getValue(0);
        long min = max;

        for (int i = 1; i < getPointCount(); i++) {
            max = Math.max(max, getValue(i));
            min = Math.min(min, getValue(i));
        }

        maxValue = new Long(max);
        minValue = new Long(min);
    }


    /**
     * Method getName
     */
    public String getName() {
        return null;
    }


    /**
     * Method getColor
     */
    public Color getColor() {
        return color;
    }


    /**
     * Method setColor
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
