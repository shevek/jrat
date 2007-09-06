package org.shiftone.jrat.provider.tree.ui.summary;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.ui.TraceTreeNode;

import java.util.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class MethodSummaryModel {

    private List methodList = new ArrayList();
    private Map methodMap = new HashMap();

    public MethodSummaryModel(TraceTreeNode node) {
        process(node);
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
        MethodSummary method = (MethodSummary) methodMap.get(methodKey);
        if (method == null) {
            method = new MethodSummary(methodKey);
            methodMap.put(methodKey, method);
            methodList.add(method);
        }
        return method;
    }


    public List getMethodSummaryList() {
        return Collections.unmodifiableList(methodList);
    }

    public Map getMethodSummaryMap() {
        return Collections.unmodifiableMap(methodMap);
    }
}
