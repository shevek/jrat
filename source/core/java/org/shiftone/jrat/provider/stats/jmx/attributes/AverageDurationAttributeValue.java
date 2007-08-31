package org.shiftone.jrat.provider.stats.jmx.attributes;


import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AverageDurationAttributeValue extends AbstractAccumulatorAttributeValue {

    public AverageDurationAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Float.class.getName(), "average execution duration in nanoseconds");
    }


    public Object getValue() {
        return accumulator.getAverageDuration();
    }
}
