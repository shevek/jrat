package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MethodSummary {

    private final MethodKey methodKey;
    private long totalEnters;
    private long totalExists;
    private long totalErrors;
    private long minDuration = Long.MAX_VALUE;
    private long maxDuration = Long.MIN_VALUE;
    private long totalDuration;
    private long totalMethodDuration;
    private int totalCallers;


    public MethodSummary(MethodKey methodKey) {
        this.methodKey = methodKey;
    }

    public void addStatistics(TraceTreeNode node) {
        Accumulator accumulator = node.getAccumulator();
        totalEnters += accumulator.getTotalEnters();
        totalExists += accumulator.getTotalExits();
        totalErrors += accumulator.getTotalErrors();
        if (totalExists > 0) {
            // if the node has not been existed, then the min and max times
            // will only have the MAX_VALUE and MIN_VALUE.
            minDuration = Math.min(minDuration, accumulator.getMinDuration());
            maxDuration = Math.max(maxDuration, accumulator.getMaxDuration());
        }
        totalDuration += accumulator.getTotalDuration();
        totalMethodDuration += node.getTotalMethodDuration();
        totalCallers++;
    }


    /**
     * It the method has been entered but not exited, then it is
     * possible that the method time would end up negative.  I'm not
     * showing it at all in this case to avoid confusion.
     */
    public Long getTotalMethodDuration() {
        return totalEnters != totalExists
                ? null
                : new Long(totalMethodDuration);
    }

    public Double getAverageMethodDuration() {
        return (totalExists == 0) || (totalEnters != totalExists)
                ? null
                : new Double((double) totalMethodDuration / (double) totalExists);
    }

    public Double getAverageDuration() {
        return totalExists == 0
                ? null
                : new Double((double) totalDuration / (double) totalExists);
    }

    public Percent getErrorRate() {
        return (totalExists == 0)
                ? null
                : new Percent(((double) totalErrors * 100.0) / (double) totalExists);
    }

    public long getUncompletedCalls() {
        return totalEnters - totalExists;
    }


    public long getTotalEnters() {
        return totalEnters;
    }

    public long getTotalExists() {
        return totalExists;
    }

    public long getTotalErrors() {
        return totalErrors;
    }

    public Long getMinDuration() {
        return minDuration == Long.MAX_VALUE ? null : new Long(minDuration);
    }

    public Long getMaxDuration() {
        return maxDuration == Long.MIN_VALUE ? null : new Long(maxDuration);
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public int getTotalCallers() {
        return totalCallers;
    }

    public MethodKey getMethodKey() {
        return methodKey;
    }
}
