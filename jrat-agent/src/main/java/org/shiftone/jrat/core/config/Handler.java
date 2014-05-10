package org.shiftone.jrat.core.config;

import java.util.HashMap;
import java.util.Map;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.util.PropertyUtil;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Handler {

    private static final Logger LOG = Logger.getLogger(Handler.class);
    private String className;
    private final Map properties = new HashMap();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map getProperties() {
        return properties;
    }

    /**
     * column a new instance of a configured factory
     */
    public MethodHandlerFactory buildMethodHandlerFactory() {

        Object instance = ResourceUtil.newInstance(className);

        PropertyUtil.setProperties(instance, properties);

        return (MethodHandlerFactory) instance;

    }

}
