package org.shiftone.jrat.ui.viewer;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.5 $
 */
public class RepaintRunnable implements Runnable {

    private Runnable runnable;

    public void run() {
        runnable.run();
    }
}
