package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;
import org.shiftone.jrat.util.Percent;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MethodSummary {

    private final MethodKey methodKey;
    private long totalEnters;
    private long totalExits;
    private long totalErrors;
    private long minDuration = Long.MAX_VALUE;
    private long maxDuration = Long.MIN_VALUE;
    private long totalDuration;
    private long totalSelfDuration;
    private int totalCallers;

    public MethodSummary(MethodKey methodKey) {
        this.methodKey = methodKey;
    }

    public void addStatistics(TraceTreeNode node) {
        Accumulator accumulator = node.getAccumulator();
        totalEnters += accumulator.getTotalEnters();
        totalExits += accumulator.getTotalExits();
        totalErrors += accumulator.getTotalErrors();
        if (totalExits > 0) {
            // if the node has not been existed, then the min and max times
            // will only have the MAX_VALUE and MIN_VALUE.
            minDuration = Math.min(minDuration, accumulator.getMinDuration());
            maxDuration = Math.max(maxDuration, accumulator.getMaxDuration());
        }
        totalDuration += accumulator.getTotalDuration();
        totalSelfDuration += node.getTotalSelfDuration();
        totalCallers++;
    }

    /**
     * It the method has been entered but not exited, then it is
     * possible that the method time would end up negative.  I'm not
     * showing it at all in this case to avoid confusion.
     */
    public Long getTotalSelfDuration() {
        return totalEnters != totalExits
                ? null
                : totalSelfDuration;
    }

    public Double getMeanSelfDuration() {
        return (totalExits == 0) || (totalEnters != totalExits)
                ? null
                : (double) totalSelfDuration / (double) totalExits;
    }

    public Double getMeanDuration() {
        return totalExits == 0
                ? null
                : (double) totalDuration / (double) totalExits;
    }

    public Percent getErrorRate() {
        return (totalExits == 0)
                ? null
                : new Percent(((double) totalErrors * 100.0) / (double) totalExits);
    }

    public long getUncompletedCalls() {
        return totalEnters - totalExits;
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

    public Long getMinDuration() {
        return minDuration == Long.MAX_VALUE ? null : minDuration;
    }

    public Long getMaxDuration() {
        return maxDuration == Long.MIN_VALUE ? null : maxDuration;
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
