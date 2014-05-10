package org.shiftone.jrat.core.jmx.benchmark;


import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.Clock;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Benchmark implements BenchmarkMBean {

    private static final Logger LOG = Logger.getLogger(Benchmark.class);
    private MethodHandler methodHandler;
    private long iterations = 1000000;

    private void doWork() {
        ;
    }


    public void monitorDoWork() {

        methodHandler.onMethodStart();

        long start = Clock.currentTimeMillis();

        doWork();
        methodHandler.onMethodFinish(Clock.currentTimeMillis() - start, null);
    }


    public long getIterations() {
        return iterations;
    }


    public void setIterations(long iterations) {
        this.iterations = iterations;
    }


    public String calculateCostPerMethodCallNanosText() {
        return "JRat is adding an overhead of about " + calculateCostPerMethodCallNanos()
                + " nanoseconds to each instrumented method call.";
    }


    public double calculateCostPerMethodCallNanos() {

        methodHandler = HandlerFactory.getMethodHandler(Benchmark.class, "doWork", "()V");

        long start;

        start = Clock.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            doWork();
        }

        long raw = Clock.currentTimeMillis() - start;

        start = Clock.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            monitorDoWork();
        }

        long jrat = Clock.currentTimeMillis() - start;
        long delta = jrat - raw;
        double each = (double) delta / (double) iterations;

        LOG.info("overhead = " + each + " nanoseconds");

        return each;
    }
}
