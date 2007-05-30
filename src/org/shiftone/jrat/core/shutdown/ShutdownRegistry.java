package org.shiftone.jrat.core.shutdown;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.HtmlUtil;
import org.shiftone.jrat.util.log.Logger;

import java.util.Stack;


/**
 * Shut down order is important!
 *
 * @author Jeff Drost
 *         <p/>
 *         http://java.sun.com/developer/JDCTechTips/2006/tt0211.html#1
 */
public class ShutdownRegistry implements ShutdownRegistryMBean {

    private static final Logger LOG = Logger.getLogger(ShutdownRegistry.class);
    private Stack shutdownStack = new Stack();
    private ShutdownListener firstShutdownListener;

    public ShutdownRegistry() {

        LOG.info("new");

        Thread shutdownHook = new Thread(new ShutdownRunnable(), "JRat-Shutdown");

        shutdownHook.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }


    public void setFirstShutdownListener(ShutdownListener firstShutdownListener) {
        this.firstShutdownListener = firstShutdownListener;
    }


    public synchronized void registerShutdownListener(ShutdownListener shutdownListener) {

        LOG.info("registerShutdownListener " + shutdownListener);
        Assert.assertNotNull("ShutdownListener", shutdownListener);
        shutdownStack.push(shutdownListener);
    }


    private static void shutdown(ShutdownListener shutdownListener) {

        try {
            LOG.info("shutting down " + shutdownListener + "...");
            shutdownListener.shutdown();
            LOG.info("shutdown " + shutdownListener + " complete.");
        }
        catch (Throwable e) {
            LOG.error("shutdown failed for " + shutdownListener, e);
            e.printStackTrace(System.err);
        }
    }


    private synchronized void shutdown() {

        LOG.info("shutting down..." + shutdownStack);

        if (firstShutdownListener != null) {
            shutdown(firstShutdownListener);
        }

        while (!shutdownStack.isEmpty()) {
            shutdown((ShutdownListener) shutdownStack.pop());
        }

        LOG.info("shutdown complete.");
        shutdownStack.clear();
    }


    private class ShutdownRunnable implements Runnable {

        public void run() {
            shutdown();
        }
    }

    public int getShutdownListenerCount() {
        return shutdownStack.size();
    }


    public String getShutdownListenersHtml() {
        return HtmlUtil.toHtml(shutdownStack);
    }


    public void forceShutdownNow() {
        LOG.info("forceShutdownNow");
        shutdown();
    }
}
