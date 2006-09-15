package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.rules.NdRulesServiceAgentEvent;

/**
 * @author Jeff Drost
 */
public class PopEventHandler implements EventHandler {
    private int popEventType;

    public PopEventHandler(int popEventType) {
        // the popEventType is the expected type of event that was previouly
        // pushed onto the stack.  This is a check to make sure things are still
        // sane.
        this.popEventType = popEventType;
    }

    public void onRulesServiceAgentEvent(NdRulesServiceAgentEvent event) {

        BlazeSessionState sessionState = BlazeState.getSessionState(event.getServiceSessionId());
        sessionState.pop(popEventType, event.getEntityName(), event.getSource());
    }
}
