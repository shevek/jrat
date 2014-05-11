package org.shiftone.jrat.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXHyperlink;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class Memory extends JPanel {

    private static final long MEG = 1024 * 1024;
    private static final java.util.Timer timer = new java.util.Timer(true);

    public static JButton createMemoryButton() {
        JButton button = new JXHyperlink();
        button.addActionListener(new GcAction());
        timer.schedule(new TickerTask(button), 1000, 1000);
        return button;
    }

    private static String toMeg(long bytes) {
        return (int) ((double) bytes / (double) MEG) + "M";
    }

    private static class GcAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.gc();
        }
    }

    private static class TickerTask extends TimerTask {

        private final JButton button;

        public TickerTask(JButton button) {
            this.button = button;
        }

        @Override
        public void run() {

            Runtime runtime = Runtime.getRuntime();

            final StringBuffer sb = new StringBuffer();

            long total = runtime.totalMemory();
            long max = runtime.maxMemory();
            long free = runtime.freeMemory();

            sb.append(toMeg(total - free));
            sb.append(" of ");
            sb.append(toMeg(max));

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    button.setText(sb.toString());
                }
            });
        }
    }

}
