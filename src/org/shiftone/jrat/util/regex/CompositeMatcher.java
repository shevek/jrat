package org.shiftone.jrat.util.regex;



import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;


/**
 * OR
 *
 * @author Jeff Drost
 *
 */
public class CompositeMatcher implements Matcher {

    private static final Logger LOG = Logger.getLogger(CompositeMatcher.class);
    private final Matcher[]     matchers;

    public static Matcher buildCompositeGlobMatcher(String pattenString) {
		return (pattenString == null) ? Matcher.ALL : buildCompositeGlobMatcher(pattenString, true);
    }


    public static Matcher buildCompositeGlobMatcher(String pattenString, boolean ignoreCase) {

        Matcher matcher;
        List    matchers = new ArrayList(10);

        if (ignoreCase)
        {
            pattenString = pattenString.toLowerCase();
        }

        StringTokenizer tokenizer = new StringTokenizer(pattenString, ",");

        while (tokenizer.hasMoreTokens())
        {
            String token = tokenizer.nextToken();

            matchers.add(new GlobMatcher(token));
        }

        if (matchers.size() == 1)
        {
            matcher = (Matcher) matchers.get(0);
        }
        else
        {
            matcher = new CompositeMatcher(matchers);
        }

        if (ignoreCase)
        {
            matcher = new ToLowerMatcher(matcher);
        }

        return matcher;
    }


    public static Matcher buildCompositeGlobMatcher(String[] pattenStrings) {

        Matcher[] matchers = new Matcher[pattenStrings.length];

        for (int i = 0; i < pattenStrings.length; i++)
        {
            matchers[i] = new GlobMatcher(pattenStrings[i]);
        }

        return new CompositeMatcher(matchers);
    }


    public CompositeMatcher(Matcher[] matchers) {
        this.matchers = matchers;
    }


    public CompositeMatcher(Collection matchers) {
        this.matchers = (Matcher[]) matchers.toArray(new Matcher[matchers.size()]);
    }


    public boolean isMatch(String inputString) {

        for (int i = 0; i < matchers.length; i++)
        {
            if (matchers[i].isMatch(inputString))
            {
                return true;
            }
        }

        return false;
    }


    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("<or-matcher>");

        for (int i = 0; i < matchers.length; i++)
        {
            sb.append(matchers[i]);
        }

        sb.append("</or-matcher>");

        return sb.toString();
    }
}
