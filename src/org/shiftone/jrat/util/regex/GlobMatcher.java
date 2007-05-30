package org.shiftone.jrat.util.regex;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;


/**
 * Class matches simple regular expressions of the form:
 * <li>this*
 * <li>* is so *
 * <li>input_file_*.dat
 * <li>com.ml.gdfs.common.util.text.*
 * <li>?he q???k br?wn fox
 * <li>java 1.4.?
 *
 * @author Jeff Drost
 */
public class GlobMatcher implements Matcher {

    private static final Logger LOG = Logger.getLogger(GlobMatcher.class);
    public static final Matcher INCLUDE_ALL = new GlobMatcher("*");
    private char[][] patternParts = null;
    private String pattenString;

    /**
     * @param pattenString initializes the matcher with the glob patterm.
     */
    public GlobMatcher(String pattenString) {

        Assert.assertNotNull("pattenString", pattenString);

        this.pattenString = pattenString;
        this.patternParts = getPatternParts(pattenString);
    }


    public boolean isMatch(String inputString) {

        Assert.assertNotNull("inputString", inputString);

        return isMatch(inputString, patternParts);
    }


    /**
     * simple method to allow glob matcher to implement FilenameFilter. Matches
     * name only.
     */
    public boolean accept(File dir, String name) {
        return isMatch(name);
    }


    private static char[][] getPatternParts(String pattenString) {

        String[] sections = StringUtil.tokenize(pattenString, "*", true);
        char[][] patternParts = new char[sections.length][];

        for (int i = 0; i < sections.length; i++) {
            patternParts[i] = sections[i].toCharArray();
        }

        return patternParts;
    }


    /**
     * <b>used by unit tests only</b>
     */
    public static boolean isMatch(String inputString, String pattenString) {
        return isMatch(inputString, getPatternParts(pattenString));
    }


    /**
     * Method isMatch
     */
    public static boolean isMatch(String input, char[][] patternParts) {

        char[] in = input.toCharArray();
        boolean canSkip = false;
        int currentIndex = 0;

        for (int i = 0; i < patternParts.length; i++) {
            if (patternParts[i][0] == '*') {
                canSkip = true;
            } else {
                if (canSkip == false) {
                    if (!matchesFixed(in, currentIndex, patternParts[i])) {
                        return false;
                    }
                } else {
                    int m = nextFixedMatch(in, currentIndex, patternParts[i]);

                    if (m == -1) {
                        return false;
                    } else {
                        currentIndex = m + patternParts[i].length;
                    }
                }

                canSkip = false;
            }
        }

        return true;
    }


    /**
     * <b>used by unit tests only</b>
     */
    public static int nextFixedMatch(String cs, int offSet, String ps) {
        return nextFixedMatch(cs.toCharArray(), offSet, ps.toCharArray());
    }


    /**
     * Method nextFixedMatch
     */
    public static int nextFixedMatch(char[] cs, int offSet, char[] ps) {

        int endIndex = cs.length - ps.length + 1;

        for (int i = offSet; i < endIndex; i++) {
            if (matchesFixed(cs, i, ps)) {
                return i;
            }
        }

        return -1;
    }


    /**
     * <b>used by unit tests only</b>
     */
    public static boolean matchesFixed(String cs, int offSet, String ps) {
        return matchesFixed(cs.toCharArray(), offSet, ps.toCharArray());
    }


    /**
     * Method matchesFixed
     */
    public static boolean matchesFixed(char[] cs, int offSet, char[] ps) {

        for (int i = 0; i < ps.length; i++) {
            int c = i + offSet;

            if (c >= cs.length) {
                return false;
            }

            if ((cs[c] != ps[i]) && (ps[i] != '?')) {
                return false;
            }
        }

        return true;
    }


    public String toString() {
        return "<glob pattern=\"" + pattenString + "\"/>";
    }
}
