package org.shiftone.jrat.core.boot.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.MethodKey;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Jeff Drost
 */
public class CacheMethodHandlerFactory implements MethodHandlerFactory {
    private static final Log LOG = LogFactory.getLog(CacheMethodHandlerFactory.class);
    private final MethodHandlerFactory methodHandlerFactory;
    private Map cache = new HashMap();

    public CacheMethodHandlerFactory(MethodHandlerFactory methodHandlerFactory) {
        this.methodHandlerFactory = methodHandlerFactory;
    }

    public synchronized MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        MethodHandler methodHandler = (MethodHandler)cache.get(methodKey);

        if (methodHandler == null) {
            methodHandler = methodHandlerFactory.createMethodHandler(methodKey);
        }

        return methodHandler;
    }

    public void startup(RuntimeContext context) throws Exception {
        methodHandlerFactory.startup(context);
    }
}
