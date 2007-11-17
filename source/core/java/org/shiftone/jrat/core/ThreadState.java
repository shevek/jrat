package org.shiftone.jrat.core;

import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class ThreadState {

    private static final Logger LOG = Logger.getLogger(ThreadState.class);

//    private static final ThreadLocal STATE = new ThreadLocal() {
//        public Object get() {
//            return new ThreadState();
//        }
//    };


    private boolean inHandler = false;
    private long clockSkew = 0;

    public static ThreadState getInstance() {
//        ThreadState state = (ThreadState) STATE.get();
//        if (state == null) {
//            throw new NullPointerException("state");
//        }
//        return state;
        return new ThreadState();
    }

    public boolean isInHandler() {
        return inHandler;
    }

    public long begin(MethodHandler methodHandler) {
//
//        long begin = System.currentTimeMillis();
//
//        methodHandler.onMethodStart();
//
//        long end = System.currentTimeMillis();
//
//        clockSkew += (end - begin);
//        return end - clockSkew;
        return 0;

    }

    public void end(MethodHandler methodHandler, long startTime, Throwable throwable) {

//
//        long begin = System.currentTimeMillis();
//        long duration = begin - startTime;
//
//        methodHandler.onMethodFinish(duration, throwable);
//
//        long end = System.currentTimeMillis();
//
//        clockSkew += (end - begin);
    }


}
