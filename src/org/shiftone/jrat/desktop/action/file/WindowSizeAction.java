package org.shiftone.jrat.desktop.action.file;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class WindowSizeAction extends AbstractAction {
    private JFrame frame;
    private Dimension size;

    public WindowSizeAction(JFrame frame, int width, int height) {
        super(width + "x" + height);
        this.frame = frame;
        this.size = new Dimension(width, height);
    }

    public void actionPerformed(ActionEvent e) {
        frame.setSize(size);
    }
}
