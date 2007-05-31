package org.shiftone.jrat.integration.blaze;

import com.blazesoft.server.rules.NdRulesServiceAgentEvent;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public class SimpleEventHandler implements EventHandler {
    private static final Logger LOG = Logger.getLogger(SimpleEventHandler.class);
    public static final EventHandler INSTANCE = new SimpleEventHandler();

    public SimpleEventHandler() {
    }

    public void onRulesServiceAgentEvent(NdRulesServiceAgentEvent event) {
        // getCode == getEventCode
        String message = event.getEventOrigin() + ":" + Codes.getOriginCodeName(event.getEventOrigin())
                + " "
                + event.getCode() + ":" + Codes.getEventCodeName(event.getCode())
                + " "
                + event.getServiceSessionId().getId()
                + " "
                + event.getServiceId()
                + " entity=" + event.getEntityName()
                + " msg=" + event.getMessage();

        LOG.info(message);

    }


}
