package org.shiftone.jrat.provider.log;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;


public class JclMethodHandler implements MethodHandler {

    private final Log       log;
    private final int       level;
    private final String    name;
    public static final int TRACE = 0;
    public static final int DEBUG = 1;
    public static final int INFO  = 2;

    public JclMethodHandler(MethodKey methodKey, String prefix, int level) {

        this.level = level;
        this.name  = methodKey.getMethodName() + methodKey.getPrettySignature();
        this.log   = LogFactory.getLog(prefix + methodKey.getClassName());
    }


    private void log(String message, Throwable throwable) {

        switch (level)
        {

        case TRACE :
            log.trace(message, throwable);
            break;

        case DEBUG :
            log.debug(message, throwable);
            break;

        case INFO :
            log.info(message, throwable);
            break;
        }
    }


    public void onMethodStart(Object obj) {
        log(name + " entered", null);
    }


    public void onMethodFinish(Object obj, long duration, Throwable throwable) {
        log(name + " finished after " + duration + "ms", throwable);
    }
}
