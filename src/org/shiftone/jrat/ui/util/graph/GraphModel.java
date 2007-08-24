package org.shiftone.jrat.ui.util.graph;


import java.awt.*;


/**
 * Interface GraphModel
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface GraphModel {

    /**
     * Method getName
     */
    String getName();


    /**
     * Method getColor
     */
    Color getColor();


    /**
     * Method getPointCount
     */
    int getPointCount();


    /**
     * Method getMaxValue
     */
    long getMaxValue();


    /**
     * Method getMinValue
     */
    long getMinValue();


    /**
     * Method getValue
     */
    long getValue(int index);
}
