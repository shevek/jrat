package org.shiftone.jrat.provider.stats.jmx.attributes;


import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MaxConcurrentThreadsAttributeValue extends AbstractAccumulatorAttributeValue {

    public MaxConcurrentThreadsAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Integer.class.getName(), "maximum number of threads that concurrent executed the method");
    }


    public Object getValue() {
        return new Integer(accumulator.getMaxConcurrentThreads());
    }
}
