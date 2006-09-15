package org.shiftone.jrat.core.criteria;



import org.shiftone.jrat.util.regex.Matcher;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class ClassMatcherMethodCriteria implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(ClassMatcherMethodCriteria.class);
    private final Matcher       matcher;

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
