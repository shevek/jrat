package org.shiftone.jrat.test;



import junit.framework.TestCase;

import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.test.dummy.DummyBean;
import org.shiftone.jrat.util.BeanWrapper;
import org.shiftone.jrat.util.log.Logger;


/**
 * Class BeanWrapperTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class BeanWrapperTestCase extends TestCase {

    private static final Logger LOG  = Logger.getLogger(BeanWrapperTestCase.class);
    DummyBean                obj  = null;
    BeanWrapper              bean = null;

    /*
       private String           one   = null;
       private String           two   = null;
       private Integer          three = null;
       private int              four  = 1;
       private Long             five  = null;
       private long             six   = 1;
       private Boolean          seven = null;
       private boolean          eight = false;
     */

    /**
     * Method setUp
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        obj  = new DummyBean();
        bean = new BeanWrapper(obj);
    }


    /**
     * Method testCreateWrapper
     *
     * @throws Exception
     */
    public void testString1() throws Exception {
        bean.setProperty("one", "12345");
        assertEquals("12345", bean.getProperty("one"));
    }


    /**
     * Method testString2
     *
     * @throws Exception
     */
    public void testString2() throws Exception {
        bean.setProperty("two", "qwerty");
        assertEquals("qwerty", bean.getProperty("two"));
    }


    /**
     * Method testInteger
     *
     * @throws Exception
     */
    public void testInteger() throws Exception {
        bean.setProperty("three", "101");
        assertEquals("101", bean.getProperty("three"));
    }


    /**
     * Method testInt
     *
     * @throws Exception
     */
    public void testInt() throws Exception {
        bean.setProperty("four", "101");
        assertEquals("101", bean.getProperty("four"));
    }


    /**
     * Method testLong
     *
     * @throws Exception
     */
    public void testLong() throws Exception {
        bean.setProperty("five", "101");
        assertEquals("101", bean.getProperty("five"));
    }


    /**
     * Method testlong
     *
     * @throws Exception
     */
    public void testlong() throws Exception {
        bean.setProperty("six", "101");
        assertEquals("101", bean.getProperty("six"));
    }


    /**
     * Method testBoolean
     *
     * @throws Exception
     */
    public void testBoolean() throws Exception {
        bean.setProperty("seven", "true");
        assertEquals("true", bean.getProperty("seven"));
    }


    /**
     * Method testboolean
     *
     * @throws Exception
     */
    public void testboolean() throws Exception {
        bean.setProperty("eight", "false");
        assertEquals("false", bean.getProperty("eight"));
    }


    /**
     * Method testFail
     *
     * @throws Exception
     */
    public void testFail() throws Exception {

        boolean setFail = false;
        boolean getFail = false;

        try
        {
            bean.setProperty("dont-exist", "false");
        }
        catch (ConfigurationException e)
        {
            LOG.debug("expected error", e);

            setFail = true;
        }

        try
        {
            bean.getProperty("dont-exist");
        }
        catch (ConfigurationException e)
        {
            LOG.debug("expected error", e);

            getFail = true;
        }

        assertTrue("get failed", getFail);
        assertTrue("set failed", setFail);
    }
}
