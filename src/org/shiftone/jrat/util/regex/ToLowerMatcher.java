package org.shiftone.jrat.util.regex;



import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.6 $
 */
public class ToLowerMatcher implements Matcher {

    private static final Logger LOG = Logger.getLogger(ToLowerMatcher.class);
    private Matcher             matcher;

    public ToLowerMatcher(Matcher matcher) {
        this.matcher = matcher;
    }


    public boolean isMatch(String inputString) {

        return (inputString == null)
               ? false
               : matcher.isMatch(inputString.toLowerCase());
    }


    public String toString() {
        return "<lower-case-matcher>" + matcher + "</lower-case-matcher>";
    }
}
