package org.shiftone.jrat.provider.stats.ui;



import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.Matchers;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.util.regex.GlobMatcher;
import org.shiftone.jrat.util.regex.ToLowerMatcher;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class StatsMatcherEditor extends ca.odell.glazedlists.matchers.AbstractMatcherEditor {

    private static final Logger LOG       = Logger.getLogger(StatsMatcherEditor.class);
    private Matcher             glMatcher = Matchers.trueMatcher();
    private final int           field;
    public static final int     FIELD_CLASS     = 0;
    public static final int     FIELD_METHOD    = 1;
    public static final int     FIELD_SIGNATURE = 2;

    public StatsMatcherEditor(int field) {
        this.field = field;
    }


    public Matcher getMatcher() {
        return glMatcher;
    }


    public void setPattern(String pattern, boolean inverse) {

        this.glMatcher = new MatcherMatcher(pattern, inverse);

        fireChanged(glMatcher);
    }


    private class MatcherMatcher implements Matcher {

        private org.shiftone.jrat.util.regex.Matcher matcher = org.shiftone.jrat.util.regex.Matcher.ALL;
        private boolean                              inverse;

        public MatcherMatcher(String pattern, boolean inverse) {
            this.matcher = new ToLowerMatcher(new GlobMatcher(pattern.toLowerCase()));
            this.inverse = inverse;
        }


        private boolean invertIfNeeded(boolean result) {

            return inverse
                   ? !result
                   : result;
        }


        public boolean matches(Object object) {

            MethodKeyAccumulator accumulator = (MethodKeyAccumulator) object;
            MethodKey            methodKey   = accumulator.getMethodKey();

            switch (field)
            {

            case FIELD_CLASS :
                return invertIfNeeded(matcher.isMatch(methodKey.getClassName()));

            case FIELD_METHOD :
                return invertIfNeeded(matcher.isMatch(methodKey.getMethodName()));

            case FIELD_SIGNATURE :
                return invertIfNeeded(matcher.isMatch(methodKey.getSignature()));
            }

            return false;
        }
    }
}
