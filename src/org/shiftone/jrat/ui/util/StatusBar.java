package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;


/**
 * Class StatusBar
 *
 * @author Jeff Drost
 */
public class StatusBar extends JPanel {

    private static final Logger LOG = Logger.getLogger(StatusBar.class);
    private JLabel status = new JLabel();
    private JProgressBar progressBar = new JProgressBar();

    /**
     * Constructor StatusBar
     */
    public StatusBar() {

        setLayout(new BorderLayout());
        status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(status, BorderLayout.CENTER);
        progressBar.setMaximumSize(new Dimension(150, 20));
        progressBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(progressBar, BorderLayout.EAST);
    }


    /**
     * Method getProgressBar
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }


    /**
     * Method setText
     */
    public void setText(String text) {
        status.setText("  " + text);
    }
}
