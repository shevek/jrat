package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.rules.NdRulesServiceAgentEvent;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public class PushEventHandler implements EventHandler {
    private static final Logger LOG = Logger.getLogger(PushEventHandler.class);

    public void onRulesServiceAgentEvent(NdRulesServiceAgentEvent event) {

        BlazeSessionState sessionState = BlazeState.getSessionState(event.getServiceSessionId());
        sessionState.push(event.getEventCode(), event.getEntityName(), event.getSource());

    }
}
