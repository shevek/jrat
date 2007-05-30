package org.shiftone.jrat.test;


import junit.framework.TestCase;
import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.core.proxy.JRatInvocationHandler;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Class TreeProviderTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class TreeProviderTestCase extends TestCase {

    private static final Logger LOG = Logger.getLogger(TreeProviderTestCase.class);


    public void testTreeProvider() throws Exception {

        System.setProperty(Settings.HANDLER_CLASS, TreeMethodHandlerFactory.class.getName());

        MethodHandler handler = HandlerFactory.getMethodHandler("java.util.ArrayList", "clear", "()V");
        List list = (List) JRatInvocationHandler.getTracedProxy(new ArrayList(), List.class);

        list.add("this");
        list.add("is");
        list.add("a");
        list.add("test");
        list.clear();
        list.add("again");
        handler.onMethodStart(null);
        list.clear();
        handler.onMethodFinish(null, 10, null);
    }
}
