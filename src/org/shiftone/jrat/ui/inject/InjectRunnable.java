package org.shiftone.jrat.ui.inject;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.util.Command;
import org.shiftone.jrat.util.Exceptions;
import org.shiftone.jrat.util.NestedRuntimeException;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.log.LoggerFactory;
import org.shiftone.jrat.util.log.target.LogTarget;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author Jeff Drost
 */
public class InjectRunnable implements Runnable, UIConstants {

    private static final Logger LOG = Logger.getLogger(InjectRunnable.class);
    private View view;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private int textOffset = 0;
    private Document document;
    private BoundedRangeModel scrollBarRangeModel;
    private Injector injector = null;
    private File[] targets;

    public InjectRunnable(Injector injector, File[] targets, View view) {

        LOG.info("InjectFilesRunnable");

        this.injector = injector;
        this.targets = targets;
        this.view = view;
        this.textArea = new JTextArea();

        this.textArea.setEditable(false);
        this.textArea.setMargin(new Insets(10, 10, 10, 10));

        // this.textArea.setBackground(Color.BLACK);
        // this.textArea.setForeground(Color.GREEN);
        this.scrollPane = new JScrollPane(textArea);
        this.scrollBarRangeModel = scrollPane.getVerticalScrollBar().getModel();
        this.document = textArea.getDocument();

        view.setBody(scrollPane);
    }


    public void run() {

        LOG.info("run");
        LoggerFactory.executeInThreadScope(new SwingLogTarget(), new Command() {

            protected void run() throws Exception {

                view.execute(new Command() {

                    public void run() {
                        doRun();
                    }
                });
                LOG.info("DONE.");
            }
        });
        LOG.info("run complete");
    }


    private void doRun() {

        Set fileSet = new TreeSet();

        for (int i = 0; i < targets.length; i++) {
            LOG.info("scanning " + targets[i] + "...");
            scan(targets[i], fileSet);
        }

        File[] files = (File[]) fileSet.toArray(new File[fileSet.size()]);
        BoundedRangeModel rangeModel = view.getRangeModel();

        LOG.info("done scanning : " + files.length + " file(s) found");
        rangeModel.setMaximum(files.length);

        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            try {
                LOG.info("inject : " + file);
                injector.inject(file);
                rangeModel.setValue(i + 1);
            }
            catch (Exception e) {
                throw new NestedRuntimeException("Error injecting file : " + file, e);
            }
        }
    }


    public synchronized void addText(String text) {

        try {
            document.insertString(textOffset, text, SimpleAttributeSet.EMPTY);

            textOffset += text.length();

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    scrollBarRangeModel.setValue(scrollBarRangeModel.getMaximum());
                }
            });
        }
        catch (Exception e) {
            LOG.error("unable to write to log document", e);
        }
    }


    private void scan(File file, Set fileSet) {

        if (file.isDirectory()) {
            LOG.info("scanning " + file.getAbsolutePath());

            File[] files = file.listFiles(INJECT_FILE_FILTER);

            for (int i = 0; i < files.length; i++) {
                scan(files[i], fileSet);
            }
        } else if (INJECT_FILE_FILTER.accept(file)) {
            fileSet.add(file);
        }
    }


    private class SwingLogTarget implements LogTarget {

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
                buffer.append(topic);
                buffer.append(" -  ");
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
