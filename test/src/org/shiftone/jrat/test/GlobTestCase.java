package org.shiftone.jrat.test;


import junit.framework.TestCase;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.GlobMatcher;


/**
 * Class GlobTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class GlobTestCase extends TestCase {

    private static final Logger LOG = Logger.getLogger(GlobTestCase.class);

    /**
     * Method testMatchesFixedStart
     *
     * @throws Exception
     */
    public void testMatchesFixedStart() throws Exception {
        matchesFixed("");
    }


    /**
     * Method testMatchesFixedOffset
     *
     * @throws Exception
     */
    public void testMatchesFixedOffset() throws Exception {
        matchesFixed("---");
    }


    /**
     * Method testMatchesFixedMoreOffset
     *
     * @throws Exception
     */
    public void testMatchesFixedMoreOffset() throws Exception {
        matchesFixed("-------");
    }


    /**
     * Method matchesFixed
     *
     * @param offStr .
     * @throws Exception
     */
    private void matchesFixed(String offStr) throws Exception {

        assertTrue(GlobMatcher.matchesFixed(offStr + "12345", offStr.length(), "12345"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "123456789", offStr.length(), "12345"));
        assertFalse(GlobMatcher.matchesFixed(offStr + "12345", offStr.length(), "123456789"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "12345", offStr.length(), "?????"));
        assertFalse(GlobMatcher.matchesFixed(offStr + "?????", offStr.length(), "12345"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "?????", offStr.length(), "?????"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "abcdef", offStr.length(), "abcdef"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "abcdef", offStr.length(), "?bcde?"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "abcdef", offStr.length(), "ab??ef"));
        assertTrue(GlobMatcher.matchesFixed(offStr + "abcdef", offStr.length(), "ab?def"));
    }


    /**
     * Method testIsMatchSimple
     *
     * @throws Exception
     */
    public void testIsMatch() throws Exception {

        assertTrue(GlobMatcher.isMatch("12345", "12345"));
        assertFalse(GlobMatcher.isMatch("12345", "54321"));
        assertTrue(GlobMatcher.isMatch("abcdefggfedcba", "a*a"));
        assertTrue(GlobMatcher.isMatch("abcdefggfedcba", "ab*ba"));
        assertTrue(GlobMatcher.isMatch("abcdefggfedcba", "*defg*"));
        assertTrue(GlobMatcher.isMatch("abcdefggfedcba", "*d??g*"));
        assertFalse(GlobMatcher.isMatch("abcdefggfedcba", "*d??X*"));
        assertTrue(GlobMatcher.isMatch("000000000xx00000", "*xx*"));
        assertTrue(GlobMatcher.isMatch("y00000000xx0000y", "y*xx*y"));
        assertFalse(GlobMatcher.isMatch("y00000000xx0000w", "y*xx*y"));
        assertTrue(GlobMatcher.isMatch("11a11", "**a**"));
        assertTrue(GlobMatcher.isMatch("abcdefg", "****"));
        assertTrue(GlobMatcher.isMatch("abcdefg", "*abcdefg*"));
        assertTrue(GlobMatcher.isMatch("abcdefg", "*********"));
        assertTrue(GlobMatcher.isMatch("abcdefg", "*"));
        assertTrue(GlobMatcher.isMatch("abcdefg", "*a*c*"));
        assertTrue(GlobMatcher.isMatch("abcdefg", "*a*c*g"));
    }


    /**
     * Method testNextFixedMatchStart
     *
     * @throws Exception
     */
    public void testNextFixedMatchStart() throws Exception {
        nextFixedMatchOffset("abcdef", 0);
    }


    /**
     * Method testNextFixedMatchOffset
     *
     * @throws Exception
     */
    public void testNextFixedMatchOffset() throws Exception {
        nextFixedMatchOffset("---abcdef", 3);
    }


    /**
     * Method nextFixedMatchOffset
     *
     * @param str    .
     * @param offset .
     * @throws Exception
     */
    private void nextFixedMatchOffset(String str, int offset) throws Exception {

        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "???"));
        assertEquals(-1, GlobMatcher.nextFixedMatch(str, offset, "xxx"));
        assertEquals(-1, GlobMatcher.nextFixedMatch(str, offset, "???????"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "abc"));
        assertEquals(offset + 1, GlobMatcher.nextFixedMatch(str, offset, "bcd"));
        assertEquals(offset + 2, GlobMatcher.nextFixedMatch(str, offset, "cde"));
        assertEquals(offset + 3, GlobMatcher.nextFixedMatch(str, offset, "def"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "?bc"));
        assertEquals(offset + 1, GlobMatcher.nextFixedMatch(str, offset, "?cd"));
        assertEquals(offset + 2, GlobMatcher.nextFixedMatch(str, offset, "?de"));
        assertEquals(offset + 3, GlobMatcher.nextFixedMatch(str, offset, "?ef"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "a?c"));
        assertEquals(offset + 1, GlobMatcher.nextFixedMatch(str, offset, "b?d"));
        assertEquals(offset + 2, GlobMatcher.nextFixedMatch(str, offset, "c?e"));
        assertEquals(offset + 3, GlobMatcher.nextFixedMatch(str, offset, "d?f"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "ab?"));
        assertEquals(offset + 1, GlobMatcher.nextFixedMatch(str, offset, "bc?"));
        assertEquals(offset + 2, GlobMatcher.nextFixedMatch(str, offset, "cd?"));
        assertEquals(offset + 3, GlobMatcher.nextFixedMatch(str, offset, "de?"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "?b?d?f"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "a?c?e?"));
        assertEquals(offset + 0, GlobMatcher.nextFixedMatch(str, offset, "???d??"));
    }
}
