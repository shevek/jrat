package org.shiftone.jrat.provider.tree;



import org.shiftone.jrat.util.Assert;


/**
 * Class DelegateThreadLocal
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.13 $
 */
public class DelegateThreadLocal extends ThreadLocal {

    private TreeMethodHandlerFactory factory = null;

    public DelegateThreadLocal(TreeMethodHandlerFactory factory) {

        Assert.assertNotNull("factory", factory);

        this.factory = factory;
    }


    protected final Object initialValue() {
        return new Delegate(factory.getRootNode());
    }
}
