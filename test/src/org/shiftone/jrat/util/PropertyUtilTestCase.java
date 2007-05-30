package org.shiftone.jrat.util;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff Drost
 */
public class PropertyUtilTestCase extends TestCase {
    private static final Log LOG = LogFactory.getLog(PropertyUtilTestCase.class);

    private String a;
    private long b;
    private int c;
    private boolean d;

    public void testSimple() throws Exception {

        Map map = new HashMap();
        map.put("a", "this is a test");
        map.put("b", "1231");
        map.put("c", "12312");
        map.put("d", "false");
        PropertyUtil.setProperties(this, map);
    }


    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public long getB() {
        return b;
    }

    public void setB(long b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public boolean getD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }
}
