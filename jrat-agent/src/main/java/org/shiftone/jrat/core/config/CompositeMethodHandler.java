package org.shiftone.jrat.core.config;

import java.util.Collection;
import org.shiftone.jrat.core.spi.MethodHandler;

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

    @Override
    public void onMethodStart() {

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].onMethodStart();
        }

    }

    @Override
    public void onMethodFinish(long durationMicros, Throwable throwable) {

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].onMethodFinish(durationMicros, throwable);
        }

    }
}
