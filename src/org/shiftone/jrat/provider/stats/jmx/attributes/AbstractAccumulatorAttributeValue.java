package org.shiftone.jrat.provider.stats.jmx.attributes;



import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.util.jmx.dynamic.AttributeValue;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public abstract class AbstractAccumulatorAttributeValue implements AttributeValue {

    protected final MethodKeyAccumulator accumulator;
    private final String                 description;
    private final String                 type;

    public AbstractAccumulatorAttributeValue(MethodKeyAccumulator accumulator, String type, String description) {

        this.accumulator = accumulator;
        this.type        = type;
        this.description = description;
    }


    public void setValue(Object value) {
        throw new RuntimeException("attribute is not writable");
    }


    public String getType() {
        return type;
    }


    public String getDescription() {
        return description;
    }


    public boolean isReadable() {
        return true;
    }


    public boolean isWritable() {
        return false;
    }
}
