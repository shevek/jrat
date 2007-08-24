package org.shiftone.jrat.ui.viewer;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class RepaintRunnable implements Runnable {

    private Runnable runnable;

    public void run() {
        runnable.run();
    }
}
