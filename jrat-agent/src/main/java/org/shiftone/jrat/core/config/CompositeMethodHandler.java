package org.shiftone.jrat.core.config;

import org.shiftone.jrat.core.spi.MethodHandler;

import java.util.Collection;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CompositeMethodHandler implements MethodHandler {

    private final MethodHandler[] handlers;

    public CompositeMethodHandler(Collection methodHandlers) {
        this((MethodHandler[]) methodHandlers.toArray(new MethodHandler[methodHandlers.size()]));
    }

    private CompositeMethodHandler(MethodHandler[] handlers) {
        this.handlers = handlers;
    }

    public void onMethodStart() {

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].onMethodStart();
        }

    }

    public void onMethodFinish(long durationMicros, Throwable throwable) {

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].onMethodFinish(durationMicros, throwable);
        }

    }
}
