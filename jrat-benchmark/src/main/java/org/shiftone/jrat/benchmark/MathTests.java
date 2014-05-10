package org.shiftone.jrat.benchmark;

import java.math.BigDecimal;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MathTests {


    @Benchmark(title = "Math.sqrt(81)")
    public void sqrt() {
        Math.sqrt(81);
    }


    @Benchmark(title = "Math.random()")
    public void random() {
        Math.random();
    }


    BigDecimal bigDecimal = BigDecimal.TEN;
    BigDecimal X = new BigDecimal("1.23456789");
    BigDecimal Y = new BigDecimal("9.87654321");


    @Benchmark(title = "BigDecimal.multiply()")
    public void multiplyBigDecimal() {
        Y.multiply(X);
    }

    @Benchmark(title = "BigDecimal.add()")
    public void addBigDecimal() {
        Y.add(X);
    }

    @Benchmark(title = "BigDecimal.add()")
        public void zzzBigDecimal() {
            Y.add(X);
        }

}
