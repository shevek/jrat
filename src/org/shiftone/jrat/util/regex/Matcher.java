package org.shiftone.jrat.util.regex;


/**
 * @author Jeff Drost
 */
public interface Matcher {

    public static final Matcher ALL = ConstantMatcher.ALL;
    public static final Matcher NONE = ConstantMatcher.NONE;

    boolean isMatch(String inputString);
}
