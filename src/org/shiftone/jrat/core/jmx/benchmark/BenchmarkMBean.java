package org.shiftone.jrat.core.jmx.benchmark;


/**
 * @author Jeff Drost
 */
public interface BenchmarkMBean {

    double calculateCostPerMethodCallNanos();


    String calculateCostPerMethodCallNanosText();


    long getIterations();


    void setIterations(long iterations);
}
