package org.shiftone.jrat.provider.log;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;


public class JclMethodHandlerFactory implements MethodHandlerFactory {

    private int level = JclMethodHandler.TRACE;
    private String prefix = "";

    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {
        return new JclMethodHandler(methodKey, prefix, level);
    }


    public void startup(RuntimeContext context) throws Exception {
    }


    public void setLevel(String levelName) {

        levelName = levelName.toLowerCase();

        if ("trace".equals(levelName)) {
            level = JclMethodHandler.TRACE;
        } else if ("debug".equals(levelName)) {
            level = JclMethodHandler.DEBUG;
        } else if ("info".equals(levelName)) {
            level = JclMethodHandler.INFO;
        } else {
            throw new JRatException("level not supported : " + levelName);
        }
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
