package org.shiftone.jrat.provider.rate.ui.graph;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.provider.rate.ui.RateModel;
import org.shiftone.jrat.ui.util.graph.AbstractGraph;


/**
 * Class ThroughputGraph
 *
 * @author Jeff Drost
 *
 */
public class ThroughputGraph extends AbstractGraph {

    private RateModel rateModel    = null;
    private int       methodNumber = 0;

    /**
     * Constructor ThroughputGraph
     *
     *
     * @param rateModel
     * @param methodNumber
     */
    public ThroughputGraph(RateModel rateModel, int methodNumber) {

        this.methodNumber = methodNumber;
        this.rateModel    = rateModel;

        setColor(rateModel.getMethodColor(methodNumber));
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

        Accumulator acc = rateModel.getAccumulator(index, methodNumber);

        return acc.getTotalExits();
    }
}
