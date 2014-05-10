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

        Iterator iterator = getCriterion().iterator();

        while (iterator.hasNext()) {
            MethodCriteria criteria = (MethodCriteria) iterator.next();

            if (!criteria.isMatch(className, modifier)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {

        Iterator iterator = getCriterion().iterator();

        while (iterator.hasNext()) {
            MethodCriteria criteria = (MethodCriteria) iterator.next();

            if (!criteria.isMatch(className, methodName, signature, modifier)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected String getTag() {
        return "and";
    }
}
