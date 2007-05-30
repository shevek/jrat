package org.shiftone.jrat.inject.bytecode;


import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public class TransformerOptions {

    private static final Logger LOG = Logger.getLogger(TransformerOptions.class);
    private MethodCriteria criteria = MethodCriteria.DEFAULT;

    public MethodCriteria getCriteria() {
        return criteria;
    }


    public void setCriteria(MethodCriteria criteria) {
        this.criteria = criteria;
    }
}
