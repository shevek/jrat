package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.util.log.Logger;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class ResizeAction
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.9 $
 */
public class ResizeAction implements ActionListener {

    private static final Logger LOG = Logger.getLogger(ResizeAction.class);
    private Frame               frame;
    private int                 width;
    private int                 height;

    /**
     * Constructor ResizeAction
     *
     *
     * @param frame
     * @param width
     * @param height
     */
    public ResizeAction(Frame frame, int width, int height) {

        this.frame  = frame;
        this.width  = width;
        this.height = height;
    }


    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e) {
        frame.setSize(width, height);
        frame.validate();
    }
}
