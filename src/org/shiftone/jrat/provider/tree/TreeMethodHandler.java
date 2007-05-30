package org.shiftone.jrat.provider.tree;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.Assert;


/**
 * @author Jeff Drost
 *
 */
public class TreeMethodHandler implements MethodHandler {

    private final TreeMethodHandlerFactory factory;
    private final MethodKey                methodKey;

    public TreeMethodHandler(TreeMethodHandlerFactory factory, MethodKey methodKey) {

        Assert.assertNotNull("factory", factory);
        Assert.assertNotNull("methodKey", methodKey);

        this.factory   = factory;
        this.methodKey = methodKey;
    }


    public void onMethodStart(Object obj) {

        Delegate delegate = factory.getDelegate();

        delegate.onMethodStart(methodKey);
    }


    public void onMethodFinish(Object obj, long durationNanos, Throwable throwable) {

        Delegate delegate = factory.getDelegate();

        delegate.onMethodFinish(methodKey, durationNanos, throwable == null);
    }
}
