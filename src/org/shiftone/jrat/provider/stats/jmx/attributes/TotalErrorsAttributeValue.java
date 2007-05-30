package org.shiftone.jrat.provider.stats.jmx.attributes;


import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author Jeff Drost
 */
public class TotalErrorsAttributeValue extends AbstractAccumulatorAttributeValue {

    public TotalErrorsAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Long.class.getName(), "number times the method threw an exception");
    }


    public Object getValue() {
        return new Long(accumulator.getTotalErrors());
    }
}
