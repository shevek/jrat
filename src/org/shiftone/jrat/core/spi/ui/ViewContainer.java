package org.shiftone.jrat.core.spi.ui;



/**
 * @author Jeff Drost
 *
 */
public interface ViewContainer {

    /** create a new view */
    View createView(String title);


    /** remove a view from the container */
    void removeView(View view);


    /** remove all views from the container */
    void removeAll();


    /** get the currently selected/displayed view */
    View getCurrentView();


    /** make the view the current view */
    void setCurrentView(View view);
}
