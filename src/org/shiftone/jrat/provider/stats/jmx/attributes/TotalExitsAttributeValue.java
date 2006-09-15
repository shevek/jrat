package org.shiftone.jrat.provider.stats.jmx.attributes;



import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class TotalExitsAttributeValue extends AbstractAccumulatorAttributeValue {

    public TotalExitsAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Long.class.getName(), "number times the method was exited");
    }


    public Object getValue() {
        return new Long(accumulator.getTotalExits());
    }
}
