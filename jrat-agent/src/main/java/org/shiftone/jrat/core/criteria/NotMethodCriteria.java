package org.shiftone.jrat.core.criteria;

import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class NotMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(NotMethodCriteria.class);
    private final MethodCriteria methodCriteria;

    public NotMethodCriteria(MethodCriteria methodCriteria) {
        this.methodCriteria = methodCriteria;
    }

    @Override
    public boolean isMatch(String className, long modifier) {
        return !methodCriteria.isMatch(className, modifier);
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return !methodCriteria.isMatch(className, methodName, signature, modifier);
    }

    @Override
    public String toString() {
        return "<not>" + methodCriteria + "</not>";
    }
}
