package org.shiftone.jrat.provider.trace;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.6 $
 */
public class TraceMethodHandlerFactory implements MethodHandlerFactory, TraceMethodHandlerFactoryMBean {

    private static final Logger LOG = Logger.getLogger(TraceMethodHandlerFactory.class);
    private RuntimeContext      context;
    private int                 callsThreshold      = 1000;
    private ThreadLocal         delegateThreadLocal = new ThreadLocal() {

        protected Object initialValue() {
            return new Delegate(context);
        }
    };

    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {
        return new TraceMethodHandler(methodKey, this);
    }


    public Delegate getDelegate() {
        return (Delegate) delegateThreadLocal.get();
    }


    public int getCallsThreshold() {
        return callsThreshold;
    }


    public void startup(RuntimeContext context) throws Exception {
        this.context = context;
    }


    public void setCallsThreshold(int callsThreshold) {
        this.callsThreshold = callsThreshold;
    }
}
