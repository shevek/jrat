package org.shiftone.jrat.benchmark;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class DoubleTests {

    private double d = 1.1d;

    @Benchmark(title = "double add")
    public void add() {
        double r = d + 1.2d;
    }

    @Benchmark(title = "double subtract")
    public void subtract() {
        double r = d - 1.2d;
    }

    @Benchmark(title = "double multiply zero")
    public void multiplyZero() {
        double r = d * 0d;
    }

    @Benchmark(title = "double multiply one")
    public void multiplyOne() {
        double r = d * 1d;
    }

    @Benchmark(title = "double multiply big")
    public void multiplyBig() {
        double r = d * 9999999999999999999999.999999999999999999999d;
    }

    @Benchmark(title = "double multiply big r")
    public double multiplyBigReturn() {
        return d * 9999999999999999999999.999999999999999999999d;
    }
}
