package org.shiftone.jrat.test.harness;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;

/**
 * Class VirtualMethodCall
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class VirtualMethodCall {

    private static final Logger LOG = Logger.getLogger(VirtualMethodCall.class);
    private static final Random random = new Random();
    private final List<String> memoryLeak = new LinkedList<String>();
    private final List<VirtualMethodCall> children = new ArrayList<VirtualMethodCall>();
    private MethodKey methodKey = null;
    private MethodHandler handler = null;
    private final long childCalls;
    private final long minDuration;
    private final long randDuration;

    public VirtualMethodCall(String className, String methodName, String signature) {

        this.methodKey = MethodKey.getInstance(className, methodName, signature);
        this.handler = HandlerFactory.getMethodHandler(methodKey);
        this.childCalls = positiveRand(20);
        this.minDuration = positiveRand(10);
        this.randDuration = positiveRand(10) + 1;
    }

    private static long positiveRand(long max) {

        // System.out.println("max = " + max);
        return (Math.abs(random.nextLong()) % max);
    }

    public void addChildMethodCall(VirtualMethodCall virtualMethodCall) {
        children.add(virtualMethodCall);
    }

    public void simulateCall() {

        long start = System.currentTimeMillis();

        handler.onMethodStart();
        simulateWork();
        simulateCallChildren();
        handler.onMethodFinish(System.currentTimeMillis() - start, null);
    }

    private void simulateWork() {

        long time = minDuration + (positiveRand(randDuration));

        try {
            Thread.sleep(time);
            memoryLeak.add(new String("leak"));
        } catch (Exception e) {
        }
    }

    private void simulateCallChildren() {

        VirtualMethodCall call = null;

        if (children.size() > 0) {
            for (int i = 0; i < childCalls; i++) {
                call = (VirtualMethodCall) children.get((int) positiveRand(children.size()));

                call.simulateCall();
            }
        }
    }
}
