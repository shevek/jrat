package org.shiftone.jrat.provider.stats.jmx.attributes;


import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MinDurationAttributeValue extends AbstractAccumulatorAttributeValue {

    public MinDurationAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Integer.class.getName(), "minimum execution time of the method in nanoseconds");
    }


    public Object getValue() {
        return new Long(accumulator.getMinDuration());
    }
}
