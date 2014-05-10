package org.shiftone.jrat.desktop.action.inject;

import org.shiftone.jrat.util.Exceptions;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;
import org.shiftone.jrat.util.log.target.LogTarget;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class RunnableLogPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(RunnableLogPanel.class);
    private JLabel label;
    private Thread thread;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Document document;
    private int textOffset = 0;
    private BoundedRangeModel scrollBarRangeModel;

    public RunnableLogPanel() {

        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        document = textArea.getDocument();
        scrollBarRangeModel = scrollPane.getVerticalScrollBar().getModel();
        label = new JLabel();

        textArea.setEditable(false);

        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    public synchronized void run(final Runnable target) {

        Runnable runnable = new Runnable() {
            public void run() {
                setLabel("Running...");
                try {
                    LoggerFactory.executeInThreadScope(new GuiLogTarget(), target);
                    setLabel("Finished.");
                } catch (Throwable e) {
                    setLabel("Failed.");
                }
            }
        };
        Thread thread = new Thread(runnable);

        thread.start();
    }


    private void setLabel(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                label.setText(text);
            }
        });
    }


    private void addText(final String text) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {

                    document.insertString(textOffset, text, SimpleAttributeSet.EMPTY);
                    textOffset += text.length();
                    scrollBarRangeModel.setValue(scrollBarRangeModel.getMaximum());

                } catch (BadLocationException e) {

                    // todo - decide what to do

                }

            }
        });
    }

    private class GuiLogTarget implements LogTarget {

        long start = System.currentTimeMillis();

        public boolean isLevelEnabled(String topic, int level) {
            return true;
        }


        public void log(String topic, int level, Object message, Throwable throwable) {

            if (isLevelEnabled(topic, level)) {
                StringBuffer buffer = new StringBuffer(80);

                buffer.append("[");
                buffer.append(Logger.LEVEL_NAMES[level]);
                buffer.append("] ");
                buffer.append(System.currentTimeMillis() - start);
                buffer.append(" ");
                //buffer.append(topic);
                //buffer.append(" -  ");
                buffer.append(String.valueOf(message));

                if (throwable != null) {
                    buffer.append("\n");
                    buffer.append(Exceptions.printStackTrace(throwable));
                }

                buffer.append('\n');
                addText(buffer.toString());
            }
        }
    }

}
