package org.shiftone.jrat.core.boot;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;

/**
 * @Author Jeff Drost
 */
public class BootTest {

    public static void main(String[] args) throws Exception {

        //HandlerFactory.initialize();

        MethodHandler a = HandlerFactory.getMethodHandler("org.test.package.AKlass", "doIt", "()V");
        MethodHandler b = HandlerFactory.getMethodHandler("org.test.package.BKlass", "doIt", "()V");
        MethodHandler c = HandlerFactory.getMethodHandler("org.test.package.CKlass", "doIt", "()V");

        for (int i = 0 ; i < 10000 ; i ++) {
            a.onMethodStart(null);
            b.onMethodStart(null);
            c.onMethodStart(null);
            c.onMethodFinish(null, 1000, null);
            b.onMethodFinish(null, 1000, null);
            a.onMethodFinish(null, 1000, null);
        
        }

    }
}
