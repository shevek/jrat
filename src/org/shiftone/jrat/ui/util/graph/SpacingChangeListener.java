package org.shiftone.jrat.ui.util.graph;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * This is a bridge between a BoundedRangeModel and a GraphComponent. Changes to
 * the value of the BoundedRangeModel cause the "pointGap" of the GraphComponent
 * component to be updated.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SpacingChangeListener implements ChangeListener {

    private static final Logger LOG = Logger.getLogger(SpacingChangeListener.class);
    private static final boolean CONTINUOUS_UPDATE = true;
    private GraphComponent graphComponent = null;

    /**
     * Constructor SpacingChangeListener
     *
     * @param graphComponent
     */
    public SpacingChangeListener(GraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }


    /**
     * Method stateChanged
     */
    public void stateChanged(ChangeEvent e) {

        Object source = e.getSource();
        BoundedRangeModel rangeModel = null;

        if (source instanceof BoundedRangeModel) {
            rangeModel = (BoundedRangeModel) source;

            if ((CONTINUOUS_UPDATE) || (rangeModel.getValueIsAdjusting() == false)) {
                graphComponent.setPointGap(rangeModel.getValue() + 1);
            }
        }
    }
}
