package org.shiftone.jrat.integration.blaze;

import com.blazesoft.engines.rules.NdRuleAgentEventCodes;
import com.blazesoft.server.base.NdServer;
import com.blazesoft.server.base.NdService;
import com.blazesoft.server.base.NdServiceAgentEvent;
import com.blazesoft.server.base.NdServiceAgentListener;
import com.blazesoft.server.rules.NdRulesServiceAgentEvent;
import org.shiftone.jrat.util.log.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff Drost
 *         <pre>
 *                 <ServiceAgentMonitorFactory>
 *                         <JavaName>org.shiftone.jrat.integration.blaze.JRatServiceAgentListener</JavaName>
 *                 </ServiceAgentMonitorFactory>
 *                 </pre>
 *         <pre>getLocalMonitoredServiceAgent(serviceName, null).
 *                     addNdServiceAgentListener(new JRatServiceAgentListener());</pre>
 */
public class JRatServiceAgentListener implements NdServiceAgentListener, NdRuleAgentEventCodes {
    private static final Logger LOG = Logger.getLogger(JRatServiceAgentListener.class);

    private static Map HANDLERS = new HashMap();


    static {

//        registerBeginEnd(NdTraceEvent.NAMESPACE_ENTRY, NdTraceEvent.NAMESPACE_EXIT);
//        registerBeginEnd(NdTraceEvent.RULESET_ENTERING_PROPAGATION, NdTraceEvent.RULESET_EXITING_PROPAGATION);
//        registerBeginEnd(NdTraceEvent.RULESET_ENTERING_FIRE, NdTraceEvent.RULESET_EXITING_FIRE);
//        registerBeginEnd(NdTraceEvent.RULE_ENTERING_FIRE, NdTraceEvent.RULE_EXITING_FIRE);
//        registerBeginEnd(NdTraceEvent.STATEMENT_EXECUTION, NdTraceEvent.STATEMENT_EXITING);
//        registerBeginEnd(NdTraceEvent.RULEFLOW_STARTING, NdTraceEvent.RULEFLOW_ENDING);
//        registerBeginEnd(NdTraceEvent.RULEFLOW_ENTERINGBLOCK, NdTraceEvent.RULEFLOW_EXITINGBLOCK);
//        registerBeginEnd(NdTraceEvent.RULEFLOW_BEGINNINGFLOW, NdTraceEvent.RULEFLOW_ENDINGFLOW);

        //registerBeginEnd(EVENT_RULE_SCHEDULE, EVENT_RULE_UNSCHEDULE);
        registerBeginEnd(EVENT_RULE_BEGIN_FIRE, EVENT_RULE_END_FIRE);
        registerBeginEnd(EVENT_OBJECT_BEGIN_INITIALIZATION, EVENT_OBJECT_END_INITIALIZATION);
        registerBeginEnd(EVENT_FUNCTIONAL_CALL, EVENT_FUNCTIONAL_RETURN);
        registerBeginEnd(EVENT_RULESET_BEGIN_PROPAGATION, EVENT_RULESET_END_PROPAGATION);
        registerBeginEnd(EVENT_RULESET_BEGIN_FIRE, EVENT_RULESET_END_FIRE);

    }


    private static void registerBeginEnd(int beginEventCode, int endEventCode) {
        EventHandler push = new PushEventHandler();
        EventHandler pop = new PopEventHandler(beginEventCode);
        register(beginEventCode, push);
        register(endEventCode, pop);
    }

    private static void register(int eventCode, EventHandler eventHandler) {
        HANDLERS.put(new Integer(eventCode), eventHandler);
    }

    public JRatServiceAgentListener() {
        this(null, null);
    }

    public JRatServiceAgentListener(NdServer server, NdService service) {
        LOG.info("new JRatServiceAgentListener");
    }

    public void onServiceAgentEvent(NdServiceAgentEvent event) {

        try {
            if (event instanceof NdRulesServiceAgentEvent) {

                NdRulesServiceAgentEvent ruleEvent = (NdRulesServiceAgentEvent) event;
                EventHandler handler = (EventHandler) HANDLERS.get(new Integer(ruleEvent.getEventCode()));

                SimpleEventHandler.INSTANCE.onRulesServiceAgentEvent(ruleEvent);

                if (handler != null) {
                    handler.onRulesServiceAgentEvent(ruleEvent);
                } else {
                    TouchEventHandler.INSTANCE.onRulesServiceAgentEvent(ruleEvent);
                    //SimpleEventHandler.INSTANCE.onRulesServiceAgentEvent(ruleEvent);
                }

            } else {

                LOG.info(event.getClientContextId()
                        + " " + event.getServiceId()
                        + " " + event.getServiceSessionId().getId());

            }

        } catch (Throwable e) {

            LOG.error("onServiceAgentEvent failed", e);

        }
    }
}
