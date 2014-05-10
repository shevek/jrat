package org.shiftone.jrat.benchmark;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class Benchmarks {


    //public List<Class> classes = new ArrayList<Class>();
    private List<BenchmarkResults> results = new ArrayList<BenchmarkResults>();

    public void add(Class[] benchmarkClass) throws Exception {
        for (Class klass : benchmarkClass ) {
            add(klass);
        }
    }

    public void add(Class benchmarkClass) throws Exception {

        for (Method method : benchmarkClass.getMethods()) {

            Benchmark benchmark = method.getAnnotation(Benchmark.class);

            if (benchmark != null) {
                results.add(new BenchmarkResults(benchmark, benchmarkClass, method));
            }

        }

    }

    public void run() throws Exception {

        for (int i = 0; i < 40; i++) {

            for (BenchmarkResults test : results) {

                System.gc();
                System.runFinalization();
                Thread.yield();
                try {
                test.run();
                } catch (Exception e) {
                    System.out.println(test);
                }
                System.out.print(".");

            }
            System.out.println();
        }

         for (BenchmarkResults test : results) {

             System.out.println(test);

         }
    }


    public static void main(String[] args) throws Exception {

        Benchmarks benchmarks = new Benchmarks();

//
//        benchmarks.add(BasicTests.class);
//
//        benchmarks.add(DoubleTests.class);
//        benchmarks.add(StringTests.class);
//        benchmarks.add(ThrowableTests.class);
//
//        benchmarks.add(MathTests.class);
//        benchmarks.add(ThreadLocalTests.class);
//
//        benchmarks.add(IntegerTests.class);

        benchmarks.add(FormatTests.CLASSES);
        benchmarks.run();

    }

}
