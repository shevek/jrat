package org.shiftone.jrat.benchmark;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class IntegerTests {


    int x = 0;

    @Benchmark(title = "Integer Increment")
    public void increment() {
        x += 1;
    }

    @Benchmark(title = "Integer Increment Return")
    public int incrementReturn() {
       return x + 5;
    }

}
