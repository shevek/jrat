package org.shiftone.jrat.util.regex;



import org.shiftone.jrat.util.log.Logger;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.5 $
 */
public class ConstantMatcher implements Matcher {

    private static final Logger LOG  = Logger.getLogger(ConstantMatcher.class);
    public static final Matcher ALL  = new ConstantMatcher(true);
    public static final Matcher NONE = new ConstantMatcher(false);
    private boolean             match;

    private ConstantMatcher(boolean match) {
        this.match = match;
    }


    public boolean isMatch(String inputString) {
        return match;
    }


    public String toString() {

        return match
               ? "ALL"
               : "NONE";
    }
}
