package org.shiftone.jrat.util.regex;

import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ToLowerMatcher implements Matcher {

    private static final Logger LOG = Logger.getLogger(ToLowerMatcher.class);
    private final Matcher matcher;

    public ToLowerMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean isMatch(String inputString) {

        return (inputString == null)
                ? false
                : matcher.isMatch(inputString.toLowerCase());
    }

    @Override
    public String toString() {
        return "<lower-case-matcher>" + matcher + "</lower-case-matcher>";
    }
}
