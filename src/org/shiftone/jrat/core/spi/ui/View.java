package org.shiftone.jrat.core.spi.ui;


import org.shiftone.jrat.util.Command;

import javax.swing.BoundedRangeModel;
import java.awt.Component;


/**
 * @author Jeff Drost
 */
public interface View {

    /**
     * change the content/body component of the view
     */
    void setBody(Component component);


    Component getBody();


    /**
     * change the title of the component
     */
    void setTitle(String title);


    String getTitle();


    /**
     * Run a task. The view will change to
     */
    Object execute(Command command);


    /**
     * Get the container of this View.
     */
    ViewContainer getContainer();


    /**
     * Get the range model that can be updated to show the user progress
     */
    BoundedRangeModel getRangeModel();
}
