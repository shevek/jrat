package org.shiftone.jrat.benchmark;

import java.lang.reflect.Method;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class BenchmarkResults {

    private final Benchmark benchmark;
    private final Class klass;
    private final Method method;
    private long total;
    private long iterations;


    public BenchmarkResults(Benchmark benchmark, Class klass, Method method) {
        this.benchmark = benchmark;
        this.klass = klass;
        this.method = method;
    }

    public void run() throws Exception {

        Object instance = klass.newInstance();

        long start = System.nanoTime();
        
        for (int i = 0; i < benchmark.iterations(); i++) {

      //      method.invoke(instance);

        }
        long end = System.nanoTime();

        this.total = (end - start);
        this.iterations += benchmark.iterations();
    }

    public String toString() {
        double each  = (double)total / (double)iterations;
        return benchmark.title() + " " + each;
    }

}
