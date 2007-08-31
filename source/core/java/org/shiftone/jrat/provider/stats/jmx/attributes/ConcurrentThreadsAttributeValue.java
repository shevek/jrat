package org.shiftone.jrat.provider.stats.jmx.attributes;


import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ConcurrentThreadsAttributeValue extends AbstractAccumulatorAttributeValue {

    public ConcurrentThreadsAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Integer.class.getName(), "number of thread currently executing this method");
    }


    public Object getValue() {
        return new Integer(accumulator.getConcurrentThreads());
    }
}
