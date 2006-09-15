package org.shiftone.jrat.util.regex;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.5 $
 */
public interface Matcher {

    public static final Matcher ALL  = ConstantMatcher.ALL;
    public static final Matcher NONE = ConstantMatcher.NONE;

    boolean isMatch(String inputString);
}
