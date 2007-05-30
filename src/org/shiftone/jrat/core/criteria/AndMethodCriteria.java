package org.shiftone.jrat.core.criteria;


import org.shiftone.jrat.util.log.Logger;

import java.util.Iterator;


/**
 * @author Jeff Drost
 */
public class AndMethodCriteria extends CompositeMethodCriteria {

    private static final Logger LOG = Logger.getLogger(AndMethodCriteria.class);

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


    protected String getTag() {
        return "and";
    }
}
