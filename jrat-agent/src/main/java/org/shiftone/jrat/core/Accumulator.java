package org.shiftone.jrat.core;

import java.io.Serializable;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Accumulator implements Serializable {

    private static final Logger LOG = Logger.getLogger(Accumulator.class);
    private static final long serialVersionUID = 2;
    private long totalEnters = 0;
    private long totalExits = 0;
    private long totalErrors = 0;
    private long minDuration = Long.MAX_VALUE;
    private long maxDuration = Long.MIN_VALUE;
    private long totalDuration = 0;    // used for mean
    private long sumOfSquares = 0;    // used for std dev
    private int concurThreads = 0;
    private int maxConcurrentThreads = 0;

    public Accumulator() {
    }

    public Accumulator(long totalEnters, long totalExits, long totalErrors,
            long totalDuration, long totalOfSquares,
            long maxDuration, long minDuration,
            int maxConcurThreads) {
        this.totalEnters = totalEnters;
        this.totalExits = totalExits;
        this.totalErrors = totalErrors;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.totalDuration = totalDuration;
        this.sumOfSquares = totalOfSquares;
        this.maxConcurrentThreads = maxConcurThreads;
    }

    /*
     * this method takes two Accumulators and smashes them together to column a
     * third.
     public void combine(Accumulator accumulator) {
     this.totalEnters += accumulator.totalEnters;
     this.totalExits += accumulator.totalExits;
     this.totalErrors += accumulator.totalErrors;
     this.minDuration = Math.min(this.minDuration, accumulator.minDuration);
     this.maxDuration = Math.max(this.maxDuration, accumulator.maxDuration);
     this.totalDuration += accumulator.totalDuration;
     this.sumOfSquares += accumulator.sumOfSquares;
     this.concurThreads += accumulator.concurThreads;
     this.maxConcurrentThreads = Math.max(this.maxConcurrentThreads, accumulator.maxConcurrentThreads);
     }
     */
    public synchronized void reset() {
        this.totalEnters = 0;
        this.totalExits = 0;
        this.totalErrors = 0;
        this.minDuration = Long.MAX_VALUE;
        this.maxDuration = Long.MIN_VALUE;
        this.totalDuration = 0;
        this.sumOfSquares = 0;
        //this.concurThreads    = this.concurThreads ;
        this.maxConcurrentThreads = this.concurThreads;
    }

    public synchronized void onMethodStart() {
        totalEnters++;
        concurThreads++;

        if (concurThreads > maxConcurrentThreads) {
            maxConcurrentThreads = concurThreads;
        }
    }

    public synchronized void onMethodFinish(long durationMs, boolean success) {
        totalExits++;

        totalDuration += durationMs;
        sumOfSquares += (durationMs * durationMs);

        if (!success) {
            totalErrors++;
        }

        if (durationMs < minDuration) {
            minDuration = durationMs;
        }

        if (durationMs > maxDuration) {
            maxDuration = durationMs;
        }

        concurThreads--;
    }

    public long getTotalEnters() {
        return totalEnters;
    }

    public long getTotalExits() {
        return totalExits;
    }

    public long getTotalErrors() {
        return totalErrors;
    }

    public long getMinDuration() {
        return minDuration;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    /** Not thread safe. */
    public double getMeanDuration() {
        if (totalExits == 0)
            return Double.NaN;
        return getTotalDuration() / totalExits;
    }

    public long getSumOfSquares() {
        return sumOfSquares;
    }

    /** Not thread safe. */
    public double getStdDeviation() {
        if (totalExits < 1)
            return Double.NaN;
        double numerator = sumOfSquares - ((double) (totalDuration * totalDuration) / (double) totalExits);
        double denominator = totalExits - 1.0;
        return Math.sqrt(numerator / denominator);
    }

    public int getConcurrentThreads() {
        return concurThreads;
    }

    public int getMaxConcurrentThreads() {
        return maxConcurrentThreads;
    }

    @Override
    public String toString() {
        return "Accumulator("
                + "totalEnters=" + totalEnters
                + ", totalExits=" + totalExits
                + ", totalErrors=" + totalErrors
                + ", minDuration=" + minDuration
                + ", maxDuration=" + maxDuration
                + ", totalDuration=" + totalDuration
                + ", maxConcurrentThreads=" + maxConcurrentThreads + ")";
    }

}
