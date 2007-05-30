package org.shiftone.jrat.test;


import junit.framework.TestCase;
import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;


/**
 * Class ConfigurationTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ConfigurationTestCase extends TestCase {

    private static final Logger LOG = Logger.getLogger(ConfigurationTestCase.class);

    /**
     * Method testGetHandlerFactory
     */
    public void testGetHandlerFactory() {

        System.setProperty(Settings.CONFIGURATION_FILE, "jrat.xml");

        MethodHandler handler1 = HandlerFactory.getMethodHandler("com.test.Class", "getIt", "(I)I");
        MethodHandler handler2 = HandlerFactory.getMethodHandler("com.test.Class", "doIt", "(I)I");
    }


    public void testNickWhiteheadGetHandlerFactory() throws Exception {

        System.setProperty(Settings.CONFIGURATION_FILE, "nick-jrat.xml");

        MethodHandler handler1 = HandlerFactory.getMethodHandler("com.adp.sbs.net5.ejb.login.Foo", "getIt", "(I)I");
        MethodHandler handler2 = HandlerFactory.getMethodHandler("com.adp.sbs.net5.ejb.login.Bar", "doIt", "(I)I");

        foo(handler1);
        foo(handler2);
    }


    private void foo(MethodHandler handler) throws Exception {

        LOG.debug("handler => " + handler);
        handler.onMethodStart(this);
        Thread.sleep(1000);
        handler.onMethodFinish(this, 1000, null);
    }
}
