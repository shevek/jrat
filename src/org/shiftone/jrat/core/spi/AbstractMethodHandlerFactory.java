package org.shiftone.jrat.core.spi;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public abstract class AbstractMethodHandlerFactory implements MethodHandlerFactory, ShutdownListener {

    private static final Logger LOG = Logger.getLogger(AbstractMethodHandlerFactory.class);
    private static final String FACTORY_POSTFIX = "Factory";
    private RuntimeContext context = null;
    private String outputFile = getDefaultOutputFile();
    public abstract MethodHandler createMethodHandler(MethodKey methodKey);


    // ------------------------------------------------------------
    public RuntimeContext getContext() {
        return context;
    }


    private String getDefaultOutputFile() {

        Class klass = this.getClass();
        String klassName = klass.getName();
        int lastDot = klassName.lastIndexOf(".");
        String name = (lastDot == -1)
                ? klassName
                : klassName.substring(lastDot + 1);

        if (name.endsWith(FACTORY_POSTFIX)) {
            name = name.substring(0, name.length() - FACTORY_POSTFIX.length());
        }
        
        return name + ".jrat";
    }


    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getOutputFile() {
        return outputFile ;
    }
   

    // ------------------------------------------------------------
    public void shutdown() {
        LOG.info("shutdown");
    }


    public void flush() {
        LOG.info("flush");
    }


    public void startup(RuntimeContext context) throws Exception {

        this.context = context;

        context.registerShutdownListener(this);
    }
}
