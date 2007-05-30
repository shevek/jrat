package org.shiftone.jrat.provider.rate.ui.graph;



import org.shiftone.jrat.provider.rate.ui.RateModel;
import org.shiftone.jrat.ui.util.graph.AbstractGraph;

import java.awt.Color;


/**
 * Class MaxMemoryGraph
 *
 * @author Jeff Drost
 *
 */
public class MaxMemoryGraph extends AbstractGraph {

    private static final Color COLOR     = new Color(0, 0, 150);
    private RateModel          rateModel = null;

    /**
     * Constructor MaxMemoryGraph
     *
     *
     * @param rateModel
     */
    public MaxMemoryGraph(RateModel rateModel) {
        this.rateModel = rateModel;
    }


    /**
     * Method getPointCount
     */
    public int getPointCount() {
        return rateModel.getSampleCount();
    }


    /**
     * Method getValue
     */
    public long getValue(int index) {
        return rateModel.getMaxMemory(index);
    }


    /**
     * Method getColor
     */
    public Color getColor() {
        return COLOR;
    }
}
