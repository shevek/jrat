package org.shiftone.jrat.provider.log;


import org.apache.log4j.Level;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;


/**
 * @author Jeff Drost
 */
public class Log4jMethodHandlerFactory implements MethodHandlerFactory {

    private Level level = Level.INFO;
    private String prefix = "";

    public MethodHandler createMethodHandler(MethodKey methodKey) {
        return new Log4jMethodHandler(methodKey, prefix, level);
    }


    public void startup(RuntimeContext context) throws Exception {
    }


    public void setLevel(String levelName) {
        level = Level.toLevel(levelName);
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
