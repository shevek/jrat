package org.shiftone.jrat.core.criteria;



import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class NotMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(NotMethodCriteria.class);
    private MethodCriteria      methodCriteria;

    public NotMethodCriteria(MethodCriteria methodCriteria) {
        this.methodCriteria = methodCriteria;
    }


    public boolean isMatch(String className, long modifier) {
        return !methodCriteria.isMatch(className, modifier);
    }


    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return !methodCriteria.isMatch(className, methodName, signature, modifier);
    }


    public String toString() {
        return "<not>" + methodCriteria + "</not>";
    }
}
