package org.shiftone.jrat.core.jmx.benchmark;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public interface BenchmarkMBean {

    double calculateCostPerMethodCallNanos();


    String calculateCostPerMethodCallNanosText();


    long getIterations();


    void setIterations(long iterations);
}
