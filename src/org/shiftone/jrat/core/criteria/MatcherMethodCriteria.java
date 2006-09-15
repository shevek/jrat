package org.shiftone.jrat.core.criteria;



import org.shiftone.jrat.util.regex.CompositeMatcher;
import org.shiftone.jrat.util.regex.Matcher;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public class MatcherMethodCriteria implements MethodCriteria {

    private Matcher classNameMatcher  = Matcher.ALL;
    private Matcher methodNameMatcher = Matcher.ALL;
    private Matcher signatureMatcher  = Matcher.ALL;

    public void setClassName(String classNameMatcher) {
        this.classNameMatcher = CompositeMatcher.buildCompositeGlobMatcher(classNameMatcher);
    }


    public void setMethodName(String methodNameMatcher) {
        this.methodNameMatcher = CompositeMatcher.buildCompositeGlobMatcher(methodNameMatcher);
    }


    public void setSignature(String signatureMatcher) {
        this.signatureMatcher = CompositeMatcher.buildCompositeGlobMatcher(signatureMatcher);
    }


    public boolean isMatch(String className, long modifier) {
        return classNameMatcher.isMatch(className);
    }


    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return classNameMatcher.isMatch(className) && methodNameMatcher.isMatch(methodName)
               && signatureMatcher.isMatch(signature);
    }
}
