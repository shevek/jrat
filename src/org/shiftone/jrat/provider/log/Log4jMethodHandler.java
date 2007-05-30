package org.shiftone.jrat.provider.log;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;


/**
 * @author Jeff Drost
 *
 */
public class Log4jMethodHandler implements MethodHandler {

    private final String name;
    private final Logger logger;
    private final Level  level;

    public Log4jMethodHandler(MethodKey methodKey, String prefix, Level level) {

        this.level  = level;
        this.name   = methodKey.getMethodName() + methodKey.getPrettySignature();
        this.logger = Logger.getLogger(prefix + methodKey.getClassName());
    }


    public void onMethodStart(Object obj) {
        logger.log(level, name + " entered");
    }


    public void onMethodFinish(Object obj, long duration, Throwable throwable) {
        logger.log(level, name + " finished after " + duration + "ms", throwable);
    }
}
