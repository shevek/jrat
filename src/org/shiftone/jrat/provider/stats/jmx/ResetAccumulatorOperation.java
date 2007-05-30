package org.shiftone.jrat.provider.stats.jmx;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.util.jmx.dynamic.RunnableOperation;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class ResetAccumulatorOperation extends RunnableOperation {

    private static final Logger LOG = Logger.getLogger(ResetAccumulatorOperation.class);
    private Accumulator         accumulator;

    public ResetAccumulatorOperation(Accumulator accumulator) {
        this.accumulator = accumulator;
    }


    public String getDescription() {
        return "reset all statistics for this method";
    }


    public void run() {
        LOG.info("reset accumulator " + accumulator);
        accumulator.setStatistics(0, 0, 0, 0, 0, 0, 0, 0);
    }
}
