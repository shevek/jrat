package org.shiftone.jrat.test.time;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;
import org.shiftone.jrat.util.time.Clock;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * @author Jeff Drost
 */
public class Timer {
    private static final Logger LOG = Logger.getLogger(Timer.class);
    private static final long ITERATIONS = 1000000;
    private static final Runnable NOOP = new NoOpRunnable();
    private static final Runnable[] RUNNABLES = {
//            new AddToDoubleRunnable(),
//            new AddToFloatRunnable(),
//            new ClockCurrentTimeNanosRunnable(),
//            new ExceptionNewRunnable(),
//            new DecimalFormatFormatRunnable(),
//            new DecimalFormatParseRunnable(),
//            new IncrementBigDecimalRunnable(),
//            new IncrementIntegerRunnable(),
//            new IncrementLongRunnable(),
//            new MathRandomRunnable(),
            new MethodCallRunnable(),
            new MethodReflectCallRunnable(),
//            new PatternMatchesRunnable(),
//            new SimpleDateFormatFormatRunnable(),
//            new SimpleDateFormatParseRunnable(),
//            new StringConcatRunnable(),
//            new StringContainsRunnable(),
//            new StringIndexOfRunnable(),
//            new StringRegionMatchesRunnable(),
//            new SunMiscPerfCalculateTimeRunnable(),
//            new SystemCurrentTimeMillisRunnable(),
//            new SystemNanoTimeRunnable(),
//            new ThreadCurrentThreadRunnable(),
//            new ThreadSleep0Runnable(),
            new ThreadLocalGetRunnable(),
            new ThreadYieldRunnable(),
            new ThrowableNewRunnable(),
            new ThrowablePrintStackTraceRunnable(),
    };

    private static final Runnable[] SHORT_RUNNABLES = {
            new SunMiscPerfCalculateTimeRunnable(),
            new MethodCallRunnable(),
            new MethodReflectCallRunnable(),
            new ClockCurrentTimeNanosRunnable(),
            new SystemCurrentTimeMillisRunnable(),
            new SystemNanoTimeRunnable(),
    };

    public static void main(String[] args) {

        LoggerFactory.disableLogging();

        time(RUNNABLES, ITERATIONS * 10);
    }


    public static void time(Runnable[] runnables, long iterations) {


        System.out.println("{| border=\"1\"");
        System.out.println("|+ Benchmark Results");
        System.out.println("! Operation !! Average Nanoseconds");

        timeRunnable(NOOP, iterations, true);

        for (int r = 0; r < runnables.length; r++) {

            System.gc();
            timeRunnable(NOOP, iterations, false);
            timeRunnable(runnables[r], iterations, true);
        }

        timeRunnable(NOOP, iterations, true);

        System.out.println("|}");
    }

    private static void timeRunnable(Runnable runnable, long iterations, boolean printResults) {

        NumberFormat format = new DecimalFormat("#,##0.00");
        long start = System.nanoTime();


        for (int i = 0; i < iterations; i++) {
            runnable.run();
        }

        long stop = System.nanoTime();
        long time = stop - start;
        double each = (double) time / (double) iterations;

        if (printResults) {
            System.out.println("|-");
            System.out.println("| " + runnable.toString());
            System.out.println("| align=\"right\"| " + format.format(each));
        }

    }
}
