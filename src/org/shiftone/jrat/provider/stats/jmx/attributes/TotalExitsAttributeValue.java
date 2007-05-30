package org.shiftone.jrat.provider.stats.jmx.attributes;


import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author Jeff Drost
 */
public class TotalExitsAttributeValue extends AbstractAccumulatorAttributeValue {

    public TotalExitsAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Long.class.getName(), "number times the method was exited");
    }


    public Object getValue() {
        return new Long(accumulator.getTotalExits());
    }
}
