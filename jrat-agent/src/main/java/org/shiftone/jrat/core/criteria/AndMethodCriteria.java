package org.shiftone.jrat.core.criteria;

import java.util.Iterator;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AndMethodCriteria extends CompositeMethodCriteria {

    private static final Logger LOG = Logger.getLogger(AndMethodCriteria.class);

    @Override
    public boolean isMatch(String className, long modifier) {
        // LOG.info("Test: " + className + " . " + modifier);

        for (MethodCriteria criteria : getCriterion()) {
            if (!criteria.isMatch(className, modifier)) {
                // LOG.info("Fail: " + criteria);
                return false;
            }
        }

        // LOG.info("Pass: " + this);
        return true;
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        // LOG.info("Test: " + className + " . " + methodName + " . " + signature + " . " + modifier);

        for (MethodCriteria criteria : getCriterion()) {
            if (!criteria.isMatch(className, methodName, signature, modifier)) {
                // LOG.info("Fail: " + criteria);
                return false;
            }
        }

        // LOG.info("Pass: " + this);
        return true;
    }

    @Override
    protected String getTag() {
        return "and";
    }
}
