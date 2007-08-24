package org.shiftone.jrat.core.criteria;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.GlobMatcher;
import org.shiftone.jrat.util.regex.Matcher;


/**
 * Used by ant task.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MatcherMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(MatcherMethodCriteria.class);

    private Matcher classNameMatcher = Matcher.ALL;
    private Matcher methodNameMatcher = Matcher.ALL;
    private Matcher signatureMatcher = Matcher.ALL;

    public void setClassName(String classNameMatcher) {
        this.classNameMatcher = GlobMatcher.create(classNameMatcher);
    }


    public void setMethodName(String methodNameMatcher) {
        this.methodNameMatcher = GlobMatcher.create(methodNameMatcher);
    }


    public void setSignature(String signatureMatcher) {
        this.signatureMatcher = GlobMatcher.create(signatureMatcher);
    }


    public boolean isMatch(String className, long modifier) {
        return classNameMatcher.isMatch(className);
    }


    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return classNameMatcher.isMatch(className)
                && methodNameMatcher.isMatch(methodName)
                && signatureMatcher.isMatch(signature);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<MatcherMethodCriteria>");
        sb.append("<Class>");
        sb.append(classNameMatcher);
        sb.append("</Class>");
        sb.append("<Method>");
        sb.append(methodNameMatcher);
        sb.append("</Method>");
        sb.append("<Signature>");
        sb.append(signatureMatcher);
        sb.append("</Signature>");
        sb.append("</MatcherMethodCriteria>");
        return sb.toString();
    }
}
