package org.shiftone.jrat.ui.action;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class SpawnFrameAction
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SpawnFrameAction implements ActionListener {

    private static final Logger LOG = Logger.getLogger(SpawnFrameAction.class);
    private final ViewContainer viewContainer;

    public SpawnFrameAction(ViewContainer viewContainer) {
        this.viewContainer = viewContainer;
    }


    public void actionPerformed(ActionEvent e) {

        View view = viewContainer.getCurrentView();

        if (view != null) {
            Component component = view.getBody();
            String title = view.getTitle();
            Dimension size = component.getSize();

            viewContainer.removeView(view);

            JFrame frame = new JFrame();

            frame.setTitle(title);
            frame.getContentPane().add(component);
            frame.setSize(size);
            frame.setVisible(true);
        }
    }
}
