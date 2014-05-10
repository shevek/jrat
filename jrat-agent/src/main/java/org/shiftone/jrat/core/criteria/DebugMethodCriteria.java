package org.shiftone.jrat.core.criteria;

import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DebugMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(DebugMethodCriteria.class);
    private final MethodCriteria criteria;

    public DebugMethodCriteria(MethodCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public boolean isMatch(String className, long modifier) {

        boolean result = criteria.isMatch(className, modifier);

        LOG.info("isMatch(" + className + " , " + modifier + " ) => " + result);

        return result;
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {

        boolean result = criteria.isMatch(className, methodName, signature, modifier);

        LOG.info("isMatch(" + className + " , " + methodName + " , " + signature + " , " + modifier + ") => " + result);

        return result;
    }
}
