package org.shiftone.jrat.ui.tab;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.util.Command;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**
 * @author Jeff Drost
 */
public class TabbedView extends JPanel implements View, ChangeListener {

    private static final Logger LOG = Logger.getLogger(TabbedView.class);
    public static final int STATE_NORMAL = 0;
    public static final int STATE_WORKING = 1;
    public static final int STATE_ERROR = 2;
    private String title;
    private String rangeText;
    private TabbedPaneViewContainer tabbedPane;
    private BoundedRangeModel rangeModel;
    private Component body;

    public TabbedView(TabbedPaneViewContainer tabbedPane, String title) {

        this.title = title;
        this.tabbedPane = tabbedPane;
        this.rangeModel = new DefaultBoundedRangeModel();

        rangeModel.addChangeListener(this);
        setLayout(new BorderLayout());
    }


    public void stateChanged(ChangeEvent e) {

        if (e.getSource() == rangeModel) {
            computeRangeText();
        }
    }


    private void computeRangeText() {

        String newRangeText = rangeText;

        if ((rangeModel.getValue() == 0) || (rangeModel.getMaximum() == 0)
                || (rangeModel.getValue() == rangeModel.getMaximum())) {
            newRangeText = "";
        } else {
            int pct = (int) (((double) rangeModel.getValue() * 100.0) / (double) rangeModel.getMaximum());

            if (pct < 0) {
                LOG.info(rangeModel.getValue() + "  " + rangeModel.getMaximum() + " => " + pct);
            }

            newRangeText = pct + "% ";
        }

        if (!newRangeText.equals(rangeText)) {
            rangeText = newRangeText;

            internalSetTitle();
        }
    }


    private int getIndex() {
        return tabbedPane.indexOfComponent(this);
    }


    public void setTitle(String newTitle) {

        this.title = newTitle;

        internalSetTitle();
    }


    public String getTitle() {
        return title;
    }


    private void internalSetTitle() {

        final String newTitle = rangeText + title;

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                tabbedPane.setTitleAt(getIndex(), newTitle);
            }
        });
    }


    public Object execute(Command command) {

        setState(STATE_WORKING);

        try {
            Object result = command.execute();

            setState(STATE_NORMAL);

            return result;
        }
        catch (RuntimeException e) {
            LOG.error("error executing " + command, e);
            setState(STATE_ERROR);

            throw e;
        }
    }


    public Component getBody() {
        return body;
    }


    public synchronized void setBody(final Component component) {

        if (body != null) {
            body.setVisible(false);
            remove(body);
        }

        body = component;

        add(body, BorderLayout.CENTER);

        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // body.setVisible(true);
        getParent().repaint();

        // }
        // });
    }


    public ViewContainer getContainer() {
        return tabbedPane;
    }


    public BoundedRangeModel getRangeModel() {
        return rangeModel;
    }


    // -----------------------------------------------------------------
    public void setState(final int state) {

        LOG.info("setState(" + state + ")");
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                if (state == STATE_WORKING) {
                    LOG.info("setState - working");

                    // setIconResource("org/shiftone/jrat/ui/icon/loading.gif");
                    setIconResource("org/shiftone/jrat/ui/icon/run.gif");
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                } else if (state == STATE_ERROR) {
                    LOG.info("setState - error");
                    setIconResource("org/shiftone/jrat/ui/icon/alert.gif");
                    setCursor(Cursor.getDefaultCursor());
                } else {
                    LOG.info("setState - normal");
                    setIconResource("org/shiftone/jrat/ui/icon/view.gif");
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
    }


    public void setIconResource(String resource) {

        byte[] imageData = ResourceUtil.loadResourceAsBytes(resource);

        setIcon(new ImageIcon(imageData, resource));
    }


    public void setIcon(Icon icon) {
        tabbedPane.setIconAt(getIndex(), icon);
    }
}
