package org.shiftone.jrat.core;


import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.TimeUnit;


/**
 * Class Accumulator
 *
 * @author Jeff Drost
 *
 */
public class Accumulator {

    private static final Logger LOG              = Logger.getLogger(Accumulator.class);
    private long                totalEnters      = 0;
    private long                totalExits       = 0;
    private long                totalErrors      = 0;
    private long                totalDuration    = 0;    // used for mean
    private long                totalOfSquaresMs = 0;    // used for std dev
    private long                maxDuration      = 0;
    private long                minDuration      = Long.MAX_VALUE;
    private int                 concurThreads    = 0;
    private int                 maxConcurThreads = 0;

    public Accumulator() {}


    public Accumulator(long totalEnters, long totalExits, long totalErrors, long totalDuration, long totalOfSquares,
                       long maxDuration, long minDuration, int maxConcurThreads) {
        setStatistics(totalEnters, totalExits, totalErrors, totalDuration,    //
                      totalOfSquares, maxDuration, minDuration, maxConcurThreads);
    }


    public void setStatistics(long totalEnters, long totalExits, long totalErrors, long totalDuration,
                              long totalOfSquares, long maxDuration, long minDuration, int maxConcurThreads) {

        this.totalEnters      = totalEnters;
        this.totalExits       = totalExits;
        this.totalErrors      = totalErrors;
        this.totalDuration    = totalDuration;
        this.totalOfSquaresMs = totalOfSquares;
        this.maxDuration      = maxDuration;
        this.minDuration      = minDuration;
        this.maxConcurThreads = maxConcurThreads;
    }


    /**
     * this method takes two Accumulators and smashes them together to create a
     * third.
     */
    public void combine(Accumulator accumulator) {

        this.totalEnters      = this.totalEnters + accumulator.totalEnters;
        this.totalExits       = this.totalExits + accumulator.totalExits;
        this.totalErrors      = this.totalErrors + accumulator.totalErrors;
        this.totalDuration    = this.totalDuration + accumulator.totalDuration;
        this.totalOfSquaresMs = this.totalOfSquaresMs + accumulator.totalOfSquaresMs;
        this.maxDuration      = Math.max(this.maxDuration, accumulator.maxDuration);
        this.minDuration      = Math.min(this.minDuration, accumulator.minDuration);
        this.concurThreads    = this.concurThreads + accumulator.concurThreads;
        this.maxConcurThreads = Math.max(this.maxConcurThreads, accumulator.maxConcurThreads);
    }

    public synchronized void reset() {

        this.totalEnters      = 0;
        this.totalExits       = 0;
        this.totalErrors      = 0;
        this.totalDuration    = 0;
        this.totalOfSquaresMs = 0;
        this.maxDuration      = 0;
        this.minDuration      = 0;
        //this.concurThreads    = this.concurThreads ;
        this.maxConcurThreads =  this.concurThreads ;

    }

    public final synchronized void onMethodStart() {

        totalEnters++;
        concurThreads++;

        if (concurThreads > maxConcurThreads)
        {
            maxConcurThreads = concurThreads;
        }
    }


    public final synchronized void onMethodFinish(long durationNanos, boolean success) {

        totalExits++;

        long durationMs = TimeUnit.MS.fromNanos(durationNanos);

        totalDuration    += durationNanos;
        totalOfSquaresMs += (durationMs * durationMs);

        if (!success)
        {
            totalErrors++;
        }

        if (durationNanos < minDuration)
        {
            minDuration = durationNanos;
        }

        if (durationNanos > maxDuration)
        {
            maxDuration = durationNanos;
        }

        concurThreads--;
    }


    public final Float getAverageDuration(TimeUnit units) {
        return TimeUnit.MS.fromNanos(getAverageDurationNanos());
    }


    public final Float getAverageDurationNanos() {

        Float average = null;

        if (totalExits > 0)
        {
            average = new Float((double) totalDuration / (double) totalExits);
        }

        return average;
    }


    public final Double getStdDeviation() {

        Double stdDeviation = null;

        if (totalExits > 1)
        {
            double numerator   = totalOfSquaresMs - ((double) (totalDuration * totalDuration) / (double) totalExits);
            double denominator = totalExits - 1.0;

            stdDeviation = new Double(Math.sqrt(numerator / denominator));
        }

        return stdDeviation;
    }


    public final int getConcurrentThreads() {
        return concurThreads;
    }


    public final long getTotalEnters() {
        return totalEnters;
    }


    public final long getTotalExits() {
        return totalExits;
    }


    public final Long getMinDuration(TimeUnit units) {
        return TimeUnit.MS.fromNanos(getMinDurationNanos());
    }


    public final Long getMinDurationNanos() {

        return (totalExits == 0)
               ? null
               : new Long(minDuration);
    }


    public final Long getMaxDuration(TimeUnit units) {
        return TimeUnit.MS.fromNanos(getMaxDurationNanos());
    }


    public final Long getMaxDurationNanos() {

        return (totalExits == 0)
               ? null
               : new Long(maxDuration);
    }


    public final long getTotalDuration(TimeUnit units) {
        return units.fromNanos(getTotalDurationNanos());
    }


    public final long getTotalDurationNanos() {
        return totalDuration;
    }


    public final long getSumOfSquares() {
        return totalOfSquaresMs;
    }


    public final long getTotalErrors() {
        return totalErrors;
    }


    public int getMaxConcurrentThreads() {
        return maxConcurThreads;
    }


    public static String toCSV(Accumulator acc) {

        StringBuffer sb = new StringBuffer(100);

        sb.append(acc.totalEnters);         // 0
        sb.append(',');
        sb.append(acc.totalExits);          // 1
        sb.append(',');
        sb.append(acc.totalErrors);         // 2
        sb.append(',');
        sb.append(acc.totalDuration);       // 3
        sb.append(',');
        sb.append(acc.totalOfSquaresMs);    // 4
        sb.append(',');
        sb.append(acc.maxConcurThreads);    // 5

        if (acc.totalExits != 0)
        {
            sb.append(',');
            sb.append(acc.maxDuration);    // 6
            sb.append(',');
            sb.append(acc.minDuration);    // 7
        }

        return sb.toString();
    }


    public String toString() {
        return "Accumulator[" + toCSV(this) + "]";
    }


    public static Accumulator fromCSV(String csv, Accumulator acc) {

        String[] tokens = StringUtil.tokenize(csv, ",", false);

        if ((tokens.length != 6) && (tokens.length != 8))
        {
            throw new JRatException("accumulator CSV must contain 6 or 8 tokens : " + tokens.length + " in " + csv);
        }

        if (tokens.length >= 6)
        {
            acc.totalEnters      = Long.parseLong(tokens[0]);
            acc.totalExits       = Long.parseLong(tokens[1]);
            acc.totalErrors      = Long.parseLong(tokens[2]);
            acc.totalDuration    = Long.parseLong(tokens[3]);
            acc.totalOfSquaresMs = Long.parseLong(tokens[4]);
            acc.maxConcurThreads = Integer.parseInt(tokens[5]);
        }

        if (tokens.length == 8)
        {
            acc.maxDuration = Long.parseLong(tokens[6]);
            acc.minDuration = Long.parseLong(tokens[7]);
        }

        return acc;
    }


    public static Accumulator fromCSV(String csv) {
        return fromCSV(csv, new Accumulator());
    }
}
