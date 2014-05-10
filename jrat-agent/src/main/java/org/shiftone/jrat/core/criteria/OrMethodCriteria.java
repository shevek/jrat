package org.shiftone.jrat.core.criteria;

import java.util.Iterator;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class OrMethodCriteria extends CompositeMethodCriteria {

    private static final Logger LOG = Logger.getLogger(OrMethodCriteria.class);

    @Override
    public boolean isMatch(String className, long modifier) {

        Iterator iterator = getCriterion().iterator();

        while (iterator.hasNext()) {
            MethodCriteria criteria = (MethodCriteria) iterator.next();

            if (criteria.isMatch(className, modifier)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {

        Iterator iterator = getCriterion().iterator();

        while (iterator.hasNext()) {
            MethodCriteria criteria = (MethodCriteria) iterator.next();

            if (criteria.isMatch(className, methodName, signature, modifier)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected String getTag() {
        return "or";
    }
}
