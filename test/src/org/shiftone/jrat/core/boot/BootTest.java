package org.shiftone.jrat.core.boot;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;

/**
 * @Author Jeff Drost
 */
public class BootTest {

    public static void main(String[] args) throws Exception {

        HandlerFactory.initialize();

        MethodHandler handler = HandlerFactory.getMethodHandler("org.test.package.Klass", "doIt", "V()");
        handler.onMethodStart(handler);
        handler.onMethodFinish(handler, 1000, null);

    }
}
