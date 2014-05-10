package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;

import java.util.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MethodSummaryModel {

    private final List methodList = new ArrayList(); // <MethodSummary>
    private final Map methodMap = new HashMap(); // <MethodKey, MethodSummary>
    private final long totalMethodDuration;

    public MethodSummaryModel(TraceTreeNode node) {
        process(node);
        totalMethodDuration = calculateTotalMethodDuration();
    }


    public long getTotalMethodDuration() {
        return totalMethodDuration;
    }

    private long calculateTotalMethodDuration() {
        long duration = 0;
        for (Iterator i = methodList.iterator(); i.hasNext();) {
            MethodSummary summary = (MethodSummary) i.next();
            Long d = (Long) summary.getTotalMethodDuration();
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
            TraceTreeNode child = node.getChildNodeAt(i);
            process(child);
        }
    }


    private MethodSummary getMethod(MethodKey methodKey) {
        MethodSummary summary = (MethodSummary) methodMap.get(methodKey);
        if (summary == null) {
            summary = new MethodSummary(methodKey);
            methodMap.put(methodKey, summary);
            methodList.add(summary);
        }
        return summary;
    }


    public List getMethodSummaryList() {
        return Collections.unmodifiableList(methodList);
    }

    public Map getMethodSummaryMap() {
        return Collections.unmodifiableMap(methodMap);
    }
}
