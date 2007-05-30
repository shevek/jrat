package org.shiftone.jrat.provider.rate.ui.graph;



import org.shiftone.jrat.provider.rate.ui.RateModel;
import org.shiftone.jrat.ui.util.graph.AbstractGraph;

import java.awt.Color;


/**
 * Class UsedMemoryGraph
 *
 * @author Jeff Drost
 *
 */
public class UsedMemoryGraph extends AbstractGraph {

    private static final Color COLOR     = new Color(0, 150, 0);
    private RateModel          rateModel = null;

    /**
     * Constructor UsedMemoryGraph
     *
     *
     * @param rateModel
     */
    public UsedMemoryGraph(RateModel rateModel) {
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
        return rateModel.getMaxMemory(index) - rateModel.getFreeMemory(index);
    }


    /**
     * Method getColor
     */
    public Color getColor() {
        return COLOR;
    }
}
