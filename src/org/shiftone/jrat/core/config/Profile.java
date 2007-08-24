package org.shiftone.jrat.core.config;

import org.shiftone.jrat.core.criteria.IncludeExcludeMethodCriteria;
import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Profile implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(Profile.class);
    private IncludeExcludeMethodCriteria methodCriteria = new IncludeExcludeMethodCriteria();
    private String name;
    private List factories = new ArrayList();

    public MatcherMethodCriteria createInclude() {
        MatcherMethodCriteria newCriteria = new MatcherMethodCriteria();
        methodCriteria.addPositive(newCriteria);
        return newCriteria;
    }


    public MatcherMethodCriteria createExclude() {
        MatcherMethodCriteria newCriteria = new MatcherMethodCriteria();
        methodCriteria.addNegative(newCriteria);
        return newCriteria;
    }

    public Handler createFactory() {
        Handler handler = new Handler();
        factories.add(handler);
        return handler;
    }

    public boolean isMatch(String className, long modifier) {
        return methodCriteria.isMatch(className, modifier);
    }

    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return methodCriteria.isMatch(className, methodName, signature, modifier);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getFactories() {
        return factories;
    }
}
