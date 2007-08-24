package org.shiftone.jrat.ui.status;


import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class StatusPanel extends JPanel {

    private static final long MEG = 1024 * 1024;
    private JLabel memory;
    private Timer timer = new Timer(true);

    public StatusPanel() {

        setLayout(new FlowLayout(FlowLayout.TRAILING));

        // setBorder(new EmptyBorder(0,0,0,0));
        timer = new Timer(true);
        memory = new JLabel();

        add(memory);
        timer.scheduleAtFixedRate(new UpdateTask(), 1000, 1000);
    }


    private class UpdateTask extends TimerTask {

        public void run() {

            Runtime runtime = Runtime.getRuntime();

            // runtime.gc();
            final StringBuffer sb = new StringBuffer();

            sb.append(" free = ");
            sb.append(toMeg(runtime.freeMemory()));
            sb.append("; max = ");
            sb.append(toMeg(runtime.maxMemory()));
            sb.append("; total = ");
            sb.append(toMeg(runtime.totalMemory()));
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    memory.setText(sb.toString());
                }
            });
        }
    }

    private String toMeg(long bytes) {
        return (int) ((double) bytes / (double) MEG) + "M";
    }
}
