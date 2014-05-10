package org.shiftone.jrat.core.criteria;


import org.shiftone.jrat.util.log.Logger;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ConstantMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(ConstantMethodCriteria.class);
    public static final MethodCriteria ALL = new ConstantMethodCriteria(true);
    public static final MethodCriteria NONE = new ConstantMethodCriteria(false);
    private boolean match;

    private ConstantMethodCriteria(boolean match) {
        this.match = match;
    }


    public boolean isMatch(String className, long modifier) {
        return match;
    }


    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return match;
    }


    public String toString() {

        return match
                ? "ALL"
                : "NONE";
    }
}
