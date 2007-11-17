package org.shiftone.jrat.provider.tree;


import org.shiftone.jrat.util.Assert;


/**
 * Class DelegateThreadLocal
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DelegateThreadLocal extends ThreadLocal {

    private TreeMethodHandlerFactory factory = null;

    public DelegateThreadLocal(TreeMethodHandlerFactory factory) {

        Assert.assertNotNull("factory", factory);

        this.factory = factory;
    }


    protected final Object initialValue() {
        return new Delegate(factory);
    }
}
