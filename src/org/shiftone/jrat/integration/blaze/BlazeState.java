package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.base.NdServiceSessionId;
import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff Drost
 */
public class BlazeState {

    private static final Logger LOG = Logger.getLogger(BlazeState.class);
    private static Map STATES = new HashMap();

    public static synchronized BlazeSessionState getSessionState(NdServiceSessionId sessionId) {
        BlazeSessionState state = (BlazeSessionState) STATES.get(sessionId);
        if (state == null) {
            LOG.info("creating session state for " + sessionId.getId());
            state = new BlazeSessionState(sessionId);
            STATES.put(sessionId, state);
        }
        return state;
    }


    public static synchronized void removeSession(NdServiceSessionId sessionId) {
        LOG.info("remove session state for " + sessionId.getId());
        STATES.remove(sessionId);
    }


    public static MethodHandler getMethodHandler(String entityName, int eventCode) {

        String className;
        String methodName;

        int lastDot = entityName.lastIndexOf('.');
        if (lastDot != -1) {
            className = "BLAZE." + entityName.substring(0, lastDot);
            methodName = entityName.substring(lastDot + 1) + " " + Codes.getEventCodeName(eventCode);
        } else {
            className = "BLAZE";
            methodName = entityName + " " + Codes.getEventCodeName(eventCode);
        }

        MethodKey methodKey = new MethodKey(className, methodName, "()V");

        return HandlerFactory.getMethodHandler(methodKey);
    }
}
