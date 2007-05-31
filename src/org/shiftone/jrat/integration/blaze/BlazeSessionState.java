package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.base.NdServiceSessionId;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.Clock;

import java.util.Stack;

/**
 * @author Jeff Drost
 */
public class BlazeSessionState {
    private static final Logger LOG = Logger.getLogger(BlazeSessionState.class);
    private Stack stack = new Stack();
    private NdServiceSessionId sessionId;

    public BlazeSessionState(NdServiceSessionId sessionId) {
        this.sessionId = sessionId;
    }

    public void push(int eventCode, String entityName, Object source) {
        StackNode node = new StackNode();
        node.eventCode = eventCode;
        node.entityName = entityName;
        node.startTime = Clock.currentTimeNanos();
        stack.push(node);
        BlazeState.getMethodHandler(entityName, eventCode).onMethodStart(source);
    }

    public void pop(int eventCode, String entityName, Object source) {

        long now = Clock.currentTimeNanos();

        StackNode node = (StackNode) stack.pop();

        if (eventCode != node.eventCode) {
            throw new RuntimeException("expected event code of "
                    + Codes.getEventCodeName(node.eventCode)
                    + " but got "
                    + Codes.getEventCodeName(eventCode));
        }
        if (!entityName.equals(node.entityName)) {
            throw new RuntimeException("expected entity name of " + node.entityName + " but got " + entityName);
        }

        BlazeState.getMethodHandler(entityName, eventCode).onMethodFinish(source, (now - node.startTime), null);

        if (stack.isEmpty()) {
            // if blaze acts as expected - I will be able to clean up
            BlazeState.removeSession(sessionId);
        }
    }

    public void touch(int eventCode, String entityName, Object source) {
        MethodHandler methodHandler = BlazeState.getMethodHandler(entityName, eventCode);
        methodHandler.onMethodStart(source);
        methodHandler.onMethodFinish(source, 0, null);
    }

    private class StackNode {
        int eventCode;
        String entityName;
        long startTime;
    }
}
