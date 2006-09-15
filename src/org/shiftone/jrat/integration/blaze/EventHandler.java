package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.rules.NdRulesServiceAgentEvent;

/**
 * @author Jeff Drost
 */
public interface EventHandler {

    public void onRulesServiceAgentEvent(NdRulesServiceAgentEvent event);

}
