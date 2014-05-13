package org.shiftone.jrat.core.criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public abstract class CompositeMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(CompositeMethodCriteria.class);
    private final List<MethodCriteria> criterion = new ArrayList<MethodCriteria>();

    public void addCriteria(MethodCriteria criteria) {
        criterion.add(criteria);
    }

    public int size() {
        return criterion.size();
    }

    public boolean isEmpty() {
        return criterion.isEmpty();
    }

    public void clear() {
        criterion.clear();
    }

    public Collection<MethodCriteria> getCriterion() {
        return Collections.unmodifiableCollection(criterion);
    }

    private MethodCriteria optimize() {

        if (criterion.isEmpty()) {
            return ConstantMethodCriteria.ALL;
        } else if (criterion.size() == 1) {
            return criterion.get(0);
        }

        return this;
    }

    protected abstract String getTag();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<" + getTag() + " size='" + criterion.size() + "'>");

        for (MethodCriteria criteria : criterion) {
            sb.append(criteria.toString());
        }

        sb.append("</" + getTag() + ">");

        return sb.toString();
    }
}
