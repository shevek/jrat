package org.shiftone.jrat.provider.stats.jmx.attributes;



import org.shiftone.jrat.core.MethodKeyAccumulator;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class ConcurrentThreadsAttributeValue extends AbstractAccumulatorAttributeValue {

    public ConcurrentThreadsAttributeValue(MethodKeyAccumulator accumulator) {
        super(accumulator, Integer.class.getName(), "number of thread currently executing this method");
    }


    public Object getValue() {
        return new Integer(accumulator.getConcurrentThreads());
    }
}
