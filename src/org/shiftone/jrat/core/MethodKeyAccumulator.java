package org.shiftone.jrat.core;



public class MethodKeyAccumulator extends Accumulator {

    protected MethodKey methodKey;

    public MethodKeyAccumulator(MethodKey methodKey) {
        this.methodKey = methodKey;
    }


    public MethodKeyAccumulator(MethodKey methodKey, long totalEnters, long totalExits, long totalErrors,
                                long totalDuration, long totalOfSquares, long maxDuration, long minDuration,
                                int maxConcurThreads) {

        super(totalEnters, totalExits, totalErrors, totalDuration, totalOfSquares, maxDuration, minDuration,
              maxConcurThreads);

        this.methodKey = methodKey;
    }


    public MethodKey getMethodKey() {
        return methodKey;
    }


    public String toString() {
        return "MethodKeyAccumulator[" + methodKey + ":" + toCSV(this) + "]";
    }
}
