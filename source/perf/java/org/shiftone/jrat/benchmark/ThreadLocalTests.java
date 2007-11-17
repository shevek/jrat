package org.shiftone.jrat.benchmark;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ThreadLocalTests {

    public static final ThreadLocal THREAD_LOCAL = new ThreadLocal() {
        protected Object initialValue() {
            return "initialValue";
        }
    };


    @Benchmark(title = "ThreadLocal.get")
    public void get() {
        THREAD_LOCAL.get();
    }

    @Benchmark(title = "ThreadLocal.set")
    public void set() {
        THREAD_LOCAL.set("newValue");
    }



}
