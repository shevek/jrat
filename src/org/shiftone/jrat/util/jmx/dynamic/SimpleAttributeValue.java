package org.shiftone.jrat.util.jmx.dynamic;



import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class SimpleAttributeValue implements AttributeValue {

    private static final Logger LOG = Logger.getLogger(SimpleAttributeValue.class);
    private Object              value;
    private String              description;
    private Class               type;

    public SimpleAttributeValue(Object value) {
        this.value = value;
    }


    public SimpleAttributeValue(Object value, String description) {
        this.value       = value;
        this.description = description;
    }


    public SimpleAttributeValue(Object value, String description, Class type) {

        this.value       = value;
        this.description = description;
        this.type        = type;
    }


    public void setValue(Object value) {
        this.value = value;
    }


    public Object getValue() {
        return value;
    }


    public String getType() {

        if (type != null)
        {
            return type.getName();
        }
        else if (value != null)
        {
            return value.getClass().getName();
        }
        else
        {
            return Object.class.getName();
        }
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isReadable() {
        return true;
    }


    public boolean isWritable() {
        return true;
    }
}
