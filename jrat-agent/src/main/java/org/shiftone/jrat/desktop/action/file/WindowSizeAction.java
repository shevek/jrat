package org.shiftone.jrat.desktop.action.file;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class WindowSizeAction extends AbstractAction {

    private final JFrame frame;
    private final Dimension size;

    public WindowSizeAction(JFrame frame, int width, int height) {
        super(width + "x" + height);
        this.frame = frame;
        this.size = new Dimension(width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setSize(size);
    }
}
