package org.shiftone.jrat.ui.viewer;


import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.util.Command;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Class OpenOutputFileRunnable
 *
 * @author Jeff Drost
 */
public class OpenOutputFileRunnable implements Runnable {

    private static final Logger LOG = Logger.getLogger(OpenOutputFileRunnable.class);
    private ViewContext viewContext;
    private View view;
    private OutputViewBuilder viewBuilder;
    private BoundedRangeModel rangeModel;
    private int percent = 0;
    private String title;

    public OpenOutputFileRunnable(ViewContext viewContext, OutputViewBuilder viewBuilder) {

        this.viewContext = viewContext;
        this.viewBuilder = viewBuilder;
        this.view = viewContext.getView();
        this.title = viewContext.getInputFile().getName();
        this.rangeModel = viewContext.getBoundedRangeModel();

        // this.rangeModel.addChangeListener(this);
    }


    // public void stateChanged(ChangeEvent e) {
    //
    // // this method is called when the rangeModel changes
    // int total = rangeModel.getMaximum() - rangeModel.getMinimum();
    // int prog = rangeModel.getValue() - rangeModel.getMinimum();
    // int newPercent = (int) ((prog * 100.0) / total);
    //
    // if (newPercent != percent)
    // {
    // percent = newPercent;
    //
    // SwingUtilities.invokeLater(new Runnable() {
    //
    // public void run() {
    //
    // if (percent != 0)
    // {
    // view.setTitle(percent + "% of " + title);
    // }
    // else
    // {
    // view.setTitle(title);
    // }
    // }
    // });
    // }
    // }
    public void run() {

        try {

            // view.setIconResource("org/shiftone/jrat/ui/icon/loading.gif");
            // view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            view.execute(new Command() {

                public void run() throws Exception {
                    viewBuilder.buildView(viewContext);
                }
            });

            // SwingUtilities.invokeLater(new Runnable() {
            // public void run() {
            // ((JComponent)viewContext.getView().getContainer()).repaint();
            // }
            // });
            // Assert.assertNotNull("component", component);
            // view.setBody(component);
            // tab.setTitle(title);
            // SwingUtilities.invokeLater(new Runnable() {
            //
            // public void run() {
            // view.setBody(component);
            // view.setIcon(null);
            // }
            // });
        }
        catch (Throwable e) {
            LOG.error("error loading file", e);

            final JEditorPane editorPane = new JEditorPane();
            final JScrollPane scrollPane = new JScrollPane(editorPane);

            scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            editorPane.setEditorKit(new HTMLEditorKit());

            StringWriter writer = new StringWriter();

            writer.write("<html><h2>Error reading file!</h2><h3>" + e.getMessage() + "</h3><pre>");
            e.printStackTrace(new PrintWriter(writer));
            writer.write("</pre></html>");
            editorPane.setText(writer.toString());
            scrollPane.setBackground(Color.WHITE);

            // SwingUtilities.invokeLater(new Runnable() {
            //
            // public void run() {
            //
            // view.setBody(scrollPane);
            // view.setIconResource("org/shiftone/jrat/ui/icon/alert.gif");
            //
            // }
            // });
        }
        finally {

            // SwingUtilities.invokeLater(new Runnable() {
            //
            // public void run() {
            // view.setCursor(Cursor.getDefaultCursor());
            // }
            // });
        }
    }
}
