package org.shiftone.jrat.core.boot.config;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.PropertyUtil;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;

import java.util.Map;
import java.util.HashMap;


/**
 * @author Jeff Drost
 */
public class Factory {

    private static final Logger LOG = Logger.getLogger(Factory.class);
    private String className;
    private Map properties = new HashMap();

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
     * create a new instance of a configured factory
     */
    public MethodHandlerFactory buildMethodHandlerFactory() throws Exception {

        Class klass = Class.forName(className);
        Object instance = klass.newInstance();

        PropertyUtil.setProperties(instance, properties);

        return (MethodHandlerFactory) instance;
    }

}
