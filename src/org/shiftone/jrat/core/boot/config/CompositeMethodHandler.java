package org.shiftone.jrat.core.boot.config;

import org.shiftone.jrat.core.spi.MethodHandler;

import java.util.Collection;

/**
 * @Author Jeff Drost
 */
public class CompositeMethodHandler implements MethodHandler {

    private final MethodHandler[] handlers;

    public CompositeMethodHandler(Collection methodHandlers) {
        this((MethodHandler[]) methodHandlers.toArray(new MethodHandler[methodHandlers.size()]));
    }

    private CompositeMethodHandler(MethodHandler[] handlers) {
        this.handlers = handlers;
    }

    public void onMethodStart(Object obj) {

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].onMethodStart(obj);
        }

    }

    public void onMethodFinish(Object target, long durationNanos, Throwable throwable) {

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].onMethodFinish(target, durationNanos, throwable);
        }

    }
}
