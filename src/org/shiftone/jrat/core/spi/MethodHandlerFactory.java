package org.shiftone.jrat.core.spi;


import org.shiftone.jrat.core.MethodKey;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface MethodHandlerFactory {

    /**
     * Method createMethodHandler
     * <li>this method on a handler will never be called more than once with
     * the same methodKey (caching need only be done at one layer)</li>
     * <li>if there is an error, or some reason not to create a handler - this
     * method should log a message and return null (this allows chain handler to
     * not log to several silent handlers)</li>
     */
    MethodHandler createMethodHandler(MethodKey methodKey) throws Exception;


    /**
     * It is recommended that handler factories that require shutdown
     * notification implement the ShutdownListener interface and call
     * context.addShutdownListener(this) in their startup method.
     *
     * @see org.shiftone.jrat.core.shutdown.ShutdownListener
     */
    void startup(RuntimeContext context) throws Exception;
}
