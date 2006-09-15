package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.rules.NdRulesServiceAgentEvent;

/**
 * @author Jeff Drost
 *         This event handler is used to simply do SOMETHING with an event, rather
 *         than let if drop on the ground.
 */
public class TouchEventHandler implements EventHandler {
    public static final EventHandler INSTANCE = new TouchEventHandler();

    public void onRulesServiceAgentEvent(NdRulesServiceAgentEvent event) {

        BlazeSessionState sessionState = BlazeState.getSessionState(event.getServiceSessionId());
        sessionState.touch(event.getEventCode(), event.getEntityName(), event.getSource());

    }
}
