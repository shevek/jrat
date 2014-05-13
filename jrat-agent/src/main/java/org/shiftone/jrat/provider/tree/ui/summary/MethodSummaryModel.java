package org.shiftone.jrat.provider.tree.ui.summary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MethodSummaryModel {

    private final List<MethodSummary> methodList = new ArrayList<MethodSummary>();
    private final Map<MethodKey, MethodSummary> methodMap = new HashMap<MethodKey, MethodSummary>();
    private final long totalSelfDuration;

    public MethodSummaryModel(TraceTreeNode node) {
        process(node);
        totalSelfDuration = calculateTotalMethodDuration();
    }

    public long getTotalSelfDuration() {
        return totalSelfDuration;
    }

    private long calculateTotalMethodDuration() {
        long duration = 0;
        for (MethodSummary summary : methodList) {
            Long d = summary.getTotalSelfDuration();
            if (d != null) {
                duration += d.longValue();
            }
        }
        return duration;
    }

    private void process(TraceTreeNode node) {

        if (!node.isRootNode()) {
            MethodKey methodKey = node.getMethodKey();
            MethodSummary method = getMethod(methodKey);
            method.addStatistics(node);
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            TraceTreeNode child = node.getChildAt(i);
            process(child);
        }
    }

    private MethodSummary getMethod(MethodKey methodKey) {
        MethodSummary summary = methodMap.get(methodKey);
        if (summary == null) {
            summary = new MethodSummary(methodKey);
            methodMap.put(methodKey, summary);
            methodList.add(summary);
        }
        return summary;
    }

    public List<? extends MethodSummary> getMethodSummaryList() {
        return Collections.unmodifiableList(methodList);
    }

    public Map<? extends MethodKey, ? extends MethodSummary> getMethodSummaryMap() {
        return Collections.unmodifiableMap(methodMap);
    }
}
