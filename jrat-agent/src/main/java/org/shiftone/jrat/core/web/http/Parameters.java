package org.shiftone.jrat.core.web.http;
import org.shiftone.jrat.util.log.Logger;

import java.util.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
class Parameters {


    private static final Logger LOG = Logger.getLogger(Parameters.class);
    private static final Parameters EMPTY = new Parameters(Collections.emptyMap());
    private final Map values; // <String, List<String>>

    public Parameters(Map values) {
        this.values = Collections.unmodifiableMap(new HashMap(values));
    }


    public static Parameters fromQueryString(String queryString) {


        if (queryString == null) {
            return EMPTY;
        }

        LOG.info("fromQueryString " + queryString );

        Map map = new HashMap();
        StringTokenizer st = new StringTokenizer(queryString, "&", false);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            add(map, token);
        }
        return new Parameters(map);
    }

    private static void add(Map values, String keyValue) {

        int equalsIndex = keyValue.indexOf('=');

        String key = keyValue.substring(0, equalsIndex);
        String value = keyValue.substring(equalsIndex + 1);

        add(values, key, value);
    }

    private static void add(Map values, String key, String value) {
        List list = (List) values.get(key);
        if (list == null) {
            list = new ArrayList();
            values.put(key, list);
        }
        list.add(decode(value));
    }

    private static String decode(String value) {

        char[] chars = value.toCharArray();
        StringBuffer sb = new StringBuffer();

        for (int i = 0 ; i < chars.length ; i ++) {

            char c = chars[i];
            if (c == '+') {
                sb.append(' ');
            } else if (c == '%') {
                String hex = "" + chars[++i] + chars[++i]; // todo
                sb.append((char) Integer.valueOf(hex, 16).intValue());
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String getValue(String key) {
        List list = (List) values.get(key);
        return (list == null)
                ? null
                : (String) list.get(0);
    }

    public String[] getValues(String key) {
        List list = (List) values.get(key);
        return (list == null)
                ? null
                : (String[]) list.toArray(new String[list.size()]);
    }

    public String toString() {
        return values.toString();
    }

    public Set getKeys() {
        return values.keySet();
    }

}
