package org.shiftone.jrat.core.jmx;



import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public class WaitingJmxRegistry implements JmxRegistry, Runnable {

    private static final Logger LOG      = Logger.getLogger(WaitingJmxRegistry.class);
    private List                waitList = new ArrayList();
    private final JmxRegistry   registry;
    private final Thread        waitThread;

    public WaitingJmxRegistry(JmxRegistry registry) {

        this.registry   = registry;
        this.waitThread = new Thread(this);

        this.waitThread.setDaemon(true);
        this.waitThread.setName("JRat-JMX-Poller");
        this.waitThread.setPriority(Thread.MIN_PRIORITY);
        this.waitThread.start();
    }


    public boolean isReady() {
        return true;
    }


    public void registerMBean(Object object, String name) {

        synchronized (this)
        {
            waitList.add(new WaitingRegisterRequest(object, name));
        }
    }


    private void registerNow() {

        List currentList = null;

        synchronized (this)
        {
            if (!waitList.isEmpty())
            {

                // if there are waiting mbeans, then "copy" the list
                currentList   = waitList;
                this.waitList = new ArrayList();
            }
        }

        if (currentList != null)
        {
            LOG.info("registering " + currentList.size() + " mbean(s)");

            for (int i = 0; i < currentList.size(); i++)
            {
                WaitingRegisterRequest request = (WaitingRegisterRequest) currentList.get(i);

                registry.registerMBean(request.object, request.name);
            }
        }
    }


    public void run() {

        int sleep = 10000;

        while (true)
        {
            try
            {
                LOG.debug("polling MBeanServer...");

                if (registry.isReady())
                {
                    registerNow();

                    return;
                }

                Thread.sleep(sleep);
            }
            catch (Throwable e)
            {
                LOG.warn("JMX poller thread encountered an error", e);
            }
        }
    }


    private class WaitingRegisterRequest {

        private Object object;
        private String name;

        public WaitingRegisterRequest(Object object, String name) {
            this.object = object;
            this.name   = name;
        }
    }
}
