package org.shiftone.jrat.core.spi.ui;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface ViewContainer {

    /**
     * column a new view
     */
    View createView(String title);


    /**
     * remove a view from the container
     */
    void removeView(View view);


    /**
     * remove all views from the container
     */
    void removeAll();


    /**
     * get the currently selected/displayed view
     */
    View getCurrentView();


    /**
     * make the view the current view
     */
    void setCurrentView(View view);
}
