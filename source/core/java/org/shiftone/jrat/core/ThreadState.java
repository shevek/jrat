package org.shiftone.jrat.core;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.spi.MethodHandler;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ThreadState {

    private static final Logger LOG = Logger.getLogger(ThreadState.class);

    private static final ThreadLocal STATE = new ThreadLocal() {
        public Object get() {
            return new ThreadState();
        }
    };


    private boolean inHandler = false;
    private long clockSkew = 0;

    public static ThreadState getInstance() {
        return (ThreadState)STATE.get();
    }    

    public boolean isInHandler() {        
        return inHandler;
    }
 
    public long begin(MethodHandler methodHandler) {

        long begin = System.currentTimeMillis();

        methodHandler.onMethodStart();

        long end = System.currentTimeMillis();

        clockSkew += (end - begin);
        return end - clockSkew;

    }

    public void end(MethodHandler methodHandler, long startTime, Throwable throwable) {


        long begin = System.currentTimeMillis();
        long duration = begin - startTime;

        methodHandler.onMethodFinish(duration, throwable);

        long end = System.currentTimeMillis();

        clockSkew += (end - begin);
    }



}
