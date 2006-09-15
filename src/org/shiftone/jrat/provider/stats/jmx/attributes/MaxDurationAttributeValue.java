package org.shiftone.jrat.provider.stats.jmx.attributes;



import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class MaxDurationAttributeValue extends AbstractAccumulatorAttributeValue {

    public MaxDurationAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Integer.class.getName(), "maximum execution time of the method in nanoseconds");
    }


    public Object getValue() {
        return accumulator.getMaxDurationNanos();
    }
}
