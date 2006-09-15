package org.shiftone.jrat.provider.stats.jmx.attributes;



import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class TotalEntersAttributeValue extends AbstractAccumulatorAttributeValue {

    public TotalEntersAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Long.class.getName(), "number of times the method was entered");
    }


    public Object getValue() {
        return new Long(accumulator.getTotalEnters());
    }
}
