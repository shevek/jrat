package org.shiftone.jrat.core.boot.config;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.criteria.IncludeExcludeMethodCriteria;
import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.shiftone.jrat.core.criteria.MethodCriteria;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jeff Drost
 */
public class Profile implements MethodCriteria {

	private static final Logger LOG = Logger.getLogger(Profile.class);
	private IncludeExcludeMethodCriteria methodCriteria = new IncludeExcludeMethodCriteria();
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

	public Factory createFactory() {
		Factory factory = new Factory();
		factories.add(factory);
		return factory;
	}

	public boolean isMatch(String className, long modifier) {
		return methodCriteria.isMatch(className, modifier);
	}

	public boolean isMatch(String className, String methodName, String signature, long modifier) {
		return methodCriteria.isMatch(className, methodName, signature, modifier);
	}


    public List getFactories() {
        return factories;
    }
}
