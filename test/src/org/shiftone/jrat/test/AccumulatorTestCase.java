package org.shiftone.jrat.test;



import junit.framework.TestCase;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.util.log.Logger;


/**
 * Class AccumulatorTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class AccumulatorTestCase extends TestCase {

    private static final Logger    LOG       = Logger.getLogger(AccumulatorTestCase.class);
    private static final double TOLERANCE = 0.00000001;

    /**
     * Method testStdDeviation0
     */
    public void testStdDeviation0() {

        Accumulator stats = new Accumulator();

        assertTrue("standard deviation of 0 numbers", stats.getStdDeviation() == null);
    }


    /**
     * Method testStdDeviation1
     */
    public void testStdDeviation1() {

        Accumulator stats = new Accumulator();

        stats.onMethodFinish(100, true);
        assertTrue("standard deviation of 1 numbers", stats.getStdDeviation() == null);
    }


    /**
     * .
     */
    public void testStdDeviation11() {

        Accumulator stats  = new Accumulator();
        double      expect = 26.84568697;
        long[]      values =
        {
            3, 4, 67, 23, 44, 56, 77, 1, 23, 44, 55
        };

        for (int i = 0; i < values.length; i++)
        {
            stats.onMethodFinish(values[i], true);
        }

        assertEquals("totalCalls", values.length, stats.getTotalExits());
        assertEquals("standard deviation of 11 numbers", expect, stats.getStdDeviation().doubleValue(), TOLERANCE);
    }


    /**
     * .
     */
    public void testStdDeviation20() {

        Accumulator stats  = new Accumulator();
        double      expect = 128047.77056645500;
        long[]      values =
        {
            1, 2, 4, 8, 16, 32, 64, 128, 256,      //
            512, 1024, 2048, 4096, 8192, 16384,    //
            32768, 65536, 131072, 262144, 524288
        };

        for (int i = 0; i < values.length; i++)
        {
            stats.onMethodFinish(values[i], true);
        }

        assertEquals("totalCalls", values.length, stats.getTotalExits());
        assertEquals("standard deviation of 20 numbers", expect, stats.getStdDeviation().doubleValue(), TOLERANCE);
    }
}
