package org.shiftone.jrat.core.criteria;

import org.shiftone.jrat.util.log.Logger;

/**
 * Used by ant task.
 * (p1 or p2 or p3 or p4) and not(n1 or n2 or n3).
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class IncludeExcludeMethodCriteria extends AndMethodCriteria {

    private static final Logger LOG = Logger.getLogger(IncludeExcludeMethodCriteria.class);
    private final OrMethodCriteria positive;
    private final OrMethodCriteria negative;

    public IncludeExcludeMethodCriteria() {
        positive = new OrMethodCriteria();
        negative = new OrMethodCriteria();

        addCriteria(positive);
        addCriteria(new NotMethodCriteria(negative));
    }

    public void addPositive(MethodCriteria criteria) {
        positive.addCriteria(criteria);
    }

    public void addNegative(MethodCriteria criteria) {
        negative.addCriteria(criteria);
    }
}
