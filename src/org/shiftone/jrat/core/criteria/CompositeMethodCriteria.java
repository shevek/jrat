package org.shiftone.jrat.core.criteria;



import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * @author Jeff Drost
 * @version $Revision: 1.3 $
 */
public abstract class CompositeMethodCriteria implements MethodCriteria {

    private static final Logger LOG       = Logger.getLogger(CompositeMethodCriteria.class);
    private List                criterion = new ArrayList();

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


    public Collection getCriterion() {
        return Collections.unmodifiableCollection(criterion);
    }


    private MethodCriteria optimize() {

        if (criterion.size() == 0)
        {
            return ConstantMethodCriteria.ALL;
        }
        else if (criterion.size() == 1)
        {
            return (MethodCriteria) criterion.get(0);
        }

        return this;
    }


    protected abstract String getTag();


    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("<" + getTag() + ">");

        for (int i = 0; i < criterion.size(); i++)
        {
            MethodCriteria criteria = (MethodCriteria) criterion.get(i);

            sb.append(criteria.toString());
        }

        sb.append("</" + getTag() + ">");

        return sb.toString();
    }
}
