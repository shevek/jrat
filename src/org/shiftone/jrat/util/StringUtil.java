package org.shiftone.jrat.util;


import org.shiftone.jrat.util.log.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * Class StringUtil
 *
 * @author Jeff Drost
 */
public class StringUtil {

    public static final String PROPERTY_DELIMITER = "|";
    public static final char DEFAULT_PAD_CHAR = ' ';
    private static final Logger LOG = Logger.getLogger(StringUtil.class);
    private static DateFormat dateFormat = new SimpleDateFormat("MMM d, yy  h:mm:ss:S aaa");
    private static final DurationUnit DU_MILLI_SECONDS = new DurationUnit(1, "ms", "ms");
    private static final DurationUnit DU_SECONDS = new DurationUnit(1000, "sec", "sec");
    private static final DurationUnit DU_MINUTES = new DurationUnit(DU_SECONDS.ms * 60, "minute", "minutes");
    private static final DurationUnit DU_HOURS = new DurationUnit(DU_MINUTES.ms * 60, "hour", "hours");
    private static final DurationUnit DU_DAYS = new DurationUnit(DU_HOURS.ms * 24, "day", "days");
    private static final DurationUnit DU_YEARS = new DurationUnit(DU_DAYS.ms * 365, "year", "years");
    private static final DurationUnit DU_DECADES = new DurationUnit(DU_YEARS.ms * 10, "decade", "decades");
    private static final DurationUnit DU_CENTURIES = new DurationUnit(DU_DECADES.ms * 10, "century",
            "centuries");
    private static final DurationUnit[] UNITS =
            {
                    DU_CENTURIES, DU_DECADES, DU_YEARS, DU_DAYS, DU_HOURS, DU_MINUTES, DU_SECONDS, DU_MILLI_SECONDS
            };
    private static final String[] SPACES = new String[16];
    private static final String[] ZEROS = new String[16];

    static {
        SPACES[0] = ZEROS[0] = "";

        for (int i = 1; i < SPACES.length; i++) {
            SPACES[i] = SPACES[i - 1] + " ";
            ZEROS[i] = ZEROS[i - 1] + "0";
        }
    }

    public static Properties parsePropertiesString(String propString) {

        Properties properties = new Properties();

        parsePropertiesString(propString, properties);

        return properties;
    }


    public static boolean isEmpty(String text) {
        return (text == null) || (text.trim().length() == 0);
    }

    public static void parsePropertiesString(String propString, Properties properties) {

        StringTokenizer tokenizer = null;
        String token = null;
        String key = null;
        String value = null;
        int eqIndex;

        Assert.assertNotNull("propString", propString);
        Assert.assertNotNull("properties", properties);

        tokenizer = new StringTokenizer(propString, PROPERTY_DELIMITER);

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            eqIndex = token.indexOf('=');

            if (eqIndex > 0) {
                key = token.substring(0, eqIndex);
                value = token.substring(eqIndex + 1);

                properties.put(key, value);
            } else {
                LOG.warn("property assignment can not be parsed from '" + token + "'");
            }
        }

        LOG.debug(properties);
    }


    public static String bufferString(int desiredLength, char padChar) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < desiredLength; i++) {
            sb.append(padChar);
        }

        return sb.toString();
    }


    public static String rightPad(String input, int desiredLength, char padChar) {

        Assert.assertNotNull("input string", input);

        String result = input;
        int padAmount = desiredLength - input.length();

        if (padAmount > 0) {
            result = bufferString(padAmount, padChar) + result;
        }

        return result;
    }


    public static String leftPad(String input, int desiredLength, char padChar) {

        Assert.assertNotNull("input string", input);

        String result = input;
        int padAmount = desiredLength - input.length();

        if (padAmount > 0) {
            result = result + bufferString(padAmount, padChar);
        }

        return result;
    }


    public static String rightPad(Object input, int desiredLength) {
        return rightPad(String.valueOf(input), desiredLength, DEFAULT_PAD_CHAR);
    }


    public static String rightPad(int input, int desiredLength) {
        return rightPad(String.valueOf(input), desiredLength, DEFAULT_PAD_CHAR);
    }


    public static String rightPad(long input, int desiredLength) {
        return rightPad(String.valueOf(input), desiredLength, DEFAULT_PAD_CHAR);
    }


    public static String leftPad(Object input, int desiredLength) {
        return leftPad(String.valueOf(input), desiredLength, DEFAULT_PAD_CHAR);
    }


    public static String leftPad(int input, int desiredLength) {
        return leftPad(String.valueOf(input), desiredLength, DEFAULT_PAD_CHAR);
    }


    public static String leftPad(long input, int desiredLength) {
        return leftPad(String.valueOf(input), desiredLength, DEFAULT_PAD_CHAR);
    }


    public static String[] tokenize(String str, String delim, boolean returnDelims) {

        StringTokenizer tokenizer = null;
        String[] matches = null;

        Assert.assertNotNull("string", str);
        Assert.assertNotNull("delimiter", delim);

        tokenizer = new StringTokenizer(str, delim, returnDelims);
        matches = new String[tokenizer.countTokens()];

        for (int i = 0; i < matches.length; i++) {
            matches[i] = tokenizer.nextToken();
        }

        return matches;
    }


    /**
     * $Revision: 1.26 $ -> 1.1
     */
    public static String revision(String rev) {

        String ver = "?";

        if ((rev != null) && (rev.startsWith("$" + "Revision: "))) {
            ver = rev.substring(11, rev.length() - 2);
        }

        return ver;
    }


    public static String booleanToString(boolean b) {

        return (b
                ? "yes"
                : "no");
    }


    public static String dateToString(long d) {
        return dateToString(new Date(d));
    }


    public static String dateToString(Date date) {

        Assert.assertNotNull("date", date);

        synchronized (dateFormat) {
            return dateFormat.format(date);
        }
    }


    public static String hex(long l) {

        String s = Long.toHexString(l);

        return "0x" + ZEROS[16 - s.length()] + s.toUpperCase();
    }


    public static String hex(int i) {

        String s = Integer.toHexString(i);

        return "0x" + ZEROS[8 - s.length()] + s.toUpperCase();
    }


    public static byte[] toBytes(String value) {

        byte[] bytes = new byte[value.length()];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) value.charAt(i);
        }

        return bytes;
    }


    public static String removeNonLetterOrDigit(String string) {

        Assert.assertNotNull("string", string);

        StringBuffer sb = new StringBuffer(string.length());
        char[] in = string.toCharArray();

        for (int i = 0; i < in.length; i++) {
            if (Character.isLetterOrDigit(in[i])) {
                sb.append(in[i]);
            }
        }

        return sb.toString();
    }


    public static String durationToString(long duration) {

        StringBuffer sb = new StringBuffer();
        boolean match = false;

        for (int i = 0; i < UNITS.length; i++) {
            DurationUnit du = UNITS[i];
            long units = duration / du.ms;

            if (units > 0) {
                duration -= (units * du.ms);

                if (match) {
                    sb.append(", ");
                }

                sb.append(units);
                sb.append(" ");

                if (units > 1) {
                    sb.append(du.pName);
                } else {
                    sb.append(du.sName);
                }

                match = true;
            }
        }

        return sb.toString();
    }
}

class DurationUnit {

    long ms;
    String sName;
    String pName;

    public DurationUnit(long ms, String sName, String pName) {

        this.ms = ms;
        this.pName = pName;
        this.sName = sName;
    }
}
