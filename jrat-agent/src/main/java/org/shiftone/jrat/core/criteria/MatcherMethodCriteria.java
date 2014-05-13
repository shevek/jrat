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
        if ("*".equals(classNameMatcher))
            this.classNameMatcher = Matcher.ALL;
        else
            this.classNameMatcher = GlobMatcher.create(classNameMatcher);
    }

    public void setMethodName(String methodNameMatcher) {
        if ("*".equals(methodNameMatcher))
            this.methodNameMatcher = Matcher.ALL;
        else
            this.methodNameMatcher = GlobMatcher.create(methodNameMatcher);
    }

    public void setSignature(String signatureMatcher) {
        if ("*".equals(signatureMatcher))
            this.signatureMatcher = Matcher.ALL;
        else
            this.signatureMatcher = GlobMatcher.create(signatureMatcher);
    }

    @Override
    public boolean isMatch(String className, long modifier) {
        return classNameMatcher.isMatch(className);
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return classNameMatcher.isMatch(className)
                && methodNameMatcher.isMatch(methodName)
                && signatureMatcher.isMatch(signature);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<MatcherMethodCriteria>");
        if (classNameMatcher != Matcher.ALL) {
            sb.append("<Class>");
            sb.append(classNameMatcher);
            sb.append("</Class>");
        }
        if (methodNameMatcher != Matcher.ALL) {
            sb.append("<Method>");
            sb.append(methodNameMatcher);
            sb.append("</Method>");
        }
        if (signatureMatcher != Matcher.ALL) {
            sb.append("<Signature>");
            sb.append(signatureMatcher);
            sb.append("</Signature>");
        }
        sb.append("</MatcherMethodCriteria>");
        return sb.toString();
    }
}
