package org.shiftone.jrat.core.criteria;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.Matcher;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ClassMatcherMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(ClassMatcherMethodCriteria.class);
    private final Matcher matcher;

    public ClassMatcherMethodCriteria(Matcher matcher) {
        this.matcher = matcher;
    }


    public boolean isMatch(String className, long modifier) {
        return matcher.isMatch(className);
    }


    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return matcher.isMatch(className);
    }


    public String toString() {
        return "<class-match>" + matcher + "</class-match>";
    }
}
