package org.shiftone.jrat.provider.config;



import org.shiftone.jrat.core.spi.MethodHandler;

import java.util.Collection;


/**
 * Class ArrayMethodHandler
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
class ArrayMethodHandler implements MethodHandler {

    private MethodHandler[] handlers = null;

    public ArrayMethodHandler(Collection methodHandlers) {
        this.handlers = (MethodHandler[]) methodHandlers.toArray(new MethodHandler[methodHandlers.size()]);
    }


    public ArrayMethodHandler(MethodHandler[] methodHandlers) {
        this.handlers = methodHandlers;
    }


    public void onMethodStart(Object obj) {

        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onMethodStart(obj);
        }
    }


    public void onMethodFinish(Object obj, long durationNanos, Throwable throwable) {

        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onMethodFinish(obj, durationNanos, throwable);
        }
    }


    public MethodHandler[] getChildHandlers() {
        return handlers;
    }
}
