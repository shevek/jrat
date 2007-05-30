package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;


/**
 * Class NSplitPane is like a JSplitPane, except it supports N splits.
 *
 * @author Jeff Drost
 *
 */
public class NSplitPane extends JPanel {

    private static final Logger LOG              = Logger.getLogger(NSplitPane.class);
    public static final int     VERTICAL_SPLIT   = JSplitPane.VERTICAL_SPLIT;
    public static final int     HORIZONTAL_SPLIT = JSplitPane.HORIZONTAL_SPLIT;
    private int                 orient           = VERTICAL_SPLIT;
    private List                children         = new ArrayList();
    private String              constraintFirst  = null;
    private String              constraintLast   = null;

    /**
     * Constructor NSplitPane
     *
     *
     * @param orient
     */
    public NSplitPane(int orient) {

        setLayout(new BorderLayout());

        this.orient          = orient;
        this.constraintFirst = (orient == VERTICAL_SPLIT)
                               ? JSplitPane.TOP
                               : JSplitPane.LEFT;
        this.constraintLast  = (orient == VERTICAL_SPLIT)
                              ? JSplitPane.BOTTOM
                              : JSplitPane.RIGHT;
    }


    /**
     * Method add
     */
    public Component add(Component component) {

        children.add(component);
        resync();

        return component;
    }


    /**
     * Method remove
     */
    public void remove(Component component) {
        children.remove(component);
        resync();
    }


    /**
     * Method resync
     */
    private void resync() {

        JSplitPane splitPane = null;
        Component  component = (Component) children.get(0);

        super.removeAll();

        for (int i = 1; i < children.size(); i++)
        {
            splitPane = new JSplitPane(orient);

            splitPane.setBorder(null);
            splitPane.setResizeWeight(i / (1d + i));
            splitPane.setOneTouchExpandable(true);
            splitPane.add(component, constraintFirst);
            splitPane.add((Component) children.get(i), constraintLast);

            component = splitPane;
        }

        super.add(component, BorderLayout.CENTER);
    }
}
