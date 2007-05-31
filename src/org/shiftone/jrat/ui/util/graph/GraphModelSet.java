package org.shiftone.jrat.ui.util.graph;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;


/**
 * Class GraphModelSet
 *
 * @author Jeff Drost
 */
public class GraphModelSet {

    private static final Logger LOG = Logger.getLogger(GraphModelSet.class);
    private Set listeners = new HashSet();
    private Long maxValue = null;    // value cache
    private Long minValue = null;    // value cache
    private Integer maxPointCount = null;    // value cache
    private Map graphMap = new HashMap();
    private Map hiddenMap = new HashMap();
    private List graphs = new ArrayList();
    private String title = null;

    /**
     * Method invalidateCache
     */
    private void invalidateCache() {
        maxValue = null;
        maxPointCount = null;
    }


    /**
     * Method addChangeListener
     */
    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }


    /**
     * Method removeChangeListener
     */
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }


    /**
     * Method fireModelChanged
     */
    private void fireModelChanged() {

        Iterator iterator = listeners.iterator();
        ChangeEvent event = new ChangeEvent(this);

        invalidateCache();

        while (iterator.hasNext()) {
            ((ChangeListener) iterator.next()).stateChanged(event);
        }
    }


    /**
     * Method getGraphModelCount
     */
    public int getGraphModelCount() {
        return graphs.size();
    }


    /**
     * Method getMaxValue
     */
    public long getMaxValue() {

        if (maxValue == null) {
            init();
        }

        return maxValue.longValue();
    }


    /**
     * Method getMinValue
     */
    public long getMinValue() {

        if (minValue == null) {
            init();
        }

        return minValue.longValue();
    }


    /**
     * Method getTitle
     */
    public String getTitle() {
        return title;
    }


    /**
     * Method setTitle
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Method init
     */
    public void init() {

        GraphModel model = null;
        long max = 0;
        long min = 0;

        if (getGraphModelCount() > 0) {
            model = getGraphModel(0);
            max = model.getMaxValue();
            min = model.getMinValue();

            for (int i = 1; i < getGraphModelCount(); i++) {
                model = getGraphModel(i);
                max = Math.max(max, model.getMaxValue());
                min = Math.min(min, model.getMinValue());
            }
        }

        maxValue = new Long(max);
        minValue = new Long(min);
    }


    /**
     * Method getMaxPointCount
     */
    public int getMaxPointCount() {

        GraphModel model = null;
        int max = 0;

        if (maxPointCount == null) {
            for (int i = 0; i < getGraphModelCount(); i++) {
                model = getGraphModel(i);
                max = Math.max(max, model.getPointCount());
            }

            maxPointCount = new Integer(max);
        }

        return maxPointCount.intValue();
    }


    /**
     * Method getGraphModel
     */
    public GraphModel getGraphModel(int index) {
        return (GraphModel) graphs.get(index);
    }


    /**
     * Method add
     */
    public synchronized void add(Object key, GraphModel graphModel) {

        if (false == graphMap.containsKey(key)) {
            graphMap.put(key, graphModel);
            graphs.add(graphModel);
            fireModelChanged();
        }
    }


    /**
     * Method remove
     */
    public synchronized void remove(Object key) {

        if (true == graphMap.containsKey(key)) {
            graphs.remove(graphMap.get(key));
            graphMap.remove(key);
            fireModelChanged();
        }
    }


    /**
     * Method hide
     */
    public synchronized void hide(Object key) {

        if (true == graphMap.containsKey(key)) {
            hiddenMap.put(key, graphMap.get(key));
            graphs.remove(graphMap.get(key));
            fireModelChanged();
        }
    }


    /**
     * Method unhide
     */
    public synchronized void unhide(Object key) {

        if (true == hiddenMap.containsKey(key)) {
            graphs.add(hiddenMap.get(key));
            hiddenMap.remove(key);
            fireModelChanged();
        }
    }
}
