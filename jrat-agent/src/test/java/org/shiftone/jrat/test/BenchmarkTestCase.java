package org.shiftone.jrat.test;


import junit.framework.TestCase;
import org.shiftone.jrat.util.log.Logger;


/**
 * This is a very simple test case designed to determine the cost of a method
 * call in comparison to an actual numeric increment.  The results indicate that
 * a line of code (j++) is about 100 times more expensive than a method call.
 * Also, if statements are just as expensive as increment.
 */
public class BenchmarkTestCase extends TestCase {

    private static final Logger LOG = Logger.getLogger(BenchmarkTestCase.class);
    private static final int ITERATIONS = 100000000;
    int i = 0;
    int j = 0;

    public void d() {

        if (i == 10) {
            j++;
        }
    }


    public void c() {
        i++;
        j++;
    }


    public void b() {
        i++;
    }


    public void a() {
        b();
    }


    public void doA() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            a();
        }

        LOG.info("doA took " + (System.currentTimeMillis() - start));
    }


    public void doB() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            b();
        }

        LOG.info("doB took " + (System.currentTimeMillis() - start));
    }


    public void doC() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            c();
        }

        LOG.info("doC took " + (System.currentTimeMillis() - start));
    }


    public void doD() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            d();
        }

        LOG.info("doD took " + (System.currentTimeMillis() - start));
    }


    public void doPlusPlus() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            j++;
        }

        LOG.info("doPlusPlus took " + (System.currentTimeMillis() - start));
    }


    public void doNothing() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            ;
        }

        LOG.info("doNothing took " + (System.currentTimeMillis() - start));
    }


    public void testAll() {

        int max = 10;

        for (int i = 0; i < max; i++) {
            doA();
            doB();
            doC();
            doD();
            doPlusPlus();
            doNothing();
        }
    }
}
