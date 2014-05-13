package org.shiftone.jrat.core.config;

import java.util.HashMap;
import java.util.Map;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CacheMethodHandlerFactory implements MethodHandlerFactory {

    private static final Logger LOG = Logger.getLogger(CacheMethodHandlerFactory.class);
    private final MethodHandlerFactory methodHandlerFactory;
    private final Map<MethodKey, MethodHandler> cache = new HashMap<MethodKey, MethodHandler>();

    public CacheMethodHandlerFactory(MethodHandlerFactory methodHandlerFactory) {
        this.methodHandlerFactory = methodHandlerFactory;
    }

    @Override
    public synchronized MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {
        MethodHandler methodHandler = cache.get(methodKey);
        if (methodHandler == null) {
            methodHandler = methodHandlerFactory.createMethodHandler(methodKey);
            cache.put(methodKey, methodHandler);
        }
        return methodHandler;
    }

    @Override
    public void startup(RuntimeContext context) throws Exception {
        LOG.info("startup");
        methodHandlerFactory.startup(context);
    }
}
