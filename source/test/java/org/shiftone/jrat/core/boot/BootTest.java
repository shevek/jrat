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
            a.onMethodStart();
            b.onMethodStart();
            c.onMethodStart();
            c.onMethodFinish(1000, null);
            b.onMethodFinish(1000, null);
            a.onMethodFinish(1000, null);
        
        }

    }
}
