package org.shiftone.jrat.integration.blaze;

import com.blazesoft.engines.rules.NdRuleAgentEventCodes;
import com.blazesoft.engines.rules.NdTraceEvent;
import com.blazesoft.server.rules.NdRulesServiceAgentEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff Drost
 */
public class Codes implements NdRuleAgentEventCodes {

    private static final Map EVENT_CODE_NAMES = new HashMap();  // I don't think these are used
    private static final Map TRACE_EVENT_NAMES = new HashMap(); // these are used
    private static final Map ORIGIN_CODE_NAMES = new HashMap(); // these are used

    static {
        registerOriginCode(NdRulesServiceAgentEvent.EVENT_ORIGIN_RULE_ENGINE, "RULE_ENGINE");
        registerOriginCode(NdRulesServiceAgentEvent.EVENT_ORIGIN_RULE_FLOW_ENGINE, "RULE_FLOW_ENGINE");
        registerOriginCode(NdRulesServiceAgentEvent.EVENT_ORIGIN_TRACE, "TRACE");

        registerEventCode(NdRuleAgentEventCodes.EVENT_NONE, "NONE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULE_SCHEDULE, "RULE_SCHEDULE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULE_UNSCHEDULE, "RULE_UNSCHEDULE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULE_BEGIN_FIRE, "RULE_BEGIN_FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULE_END_FIRE, "RULE_END_FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_CREATED, "OBJECT_CREATED");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_BEGIN_INITIALIZATION, "OBJECT_BEGIN_INITIALIZATION");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_END_INITIALIZATION, "OBJECT_END_INITIALIZATION");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_DELETED, "OBJECT_DELETED");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_PROPAGATE, "OBJECT_PROPAGATE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_RECLASSIFYING, "OBJECT_RECLASSIFYING");
        registerEventCode(NdRuleAgentEventCodes.EVENT_OBJECT_RECLASSIFIED, "OBJECT_RECLASSIFIED");
        registerEventCode(NdRuleAgentEventCodes.EVENT_PROPERTY_MODIFIED, "PROPERTY_MODIFIED");
        registerEventCode(NdRuleAgentEventCodes.EVENT_PROPERTY_QUERIED, "PROPERTY_QUERIED");
        registerEventCode(NdRuleAgentEventCodes.EVENT_PROPERTY_NOTIFY, "PROPERTY_NOTIFY");
        registerEventCode(NdRuleAgentEventCodes.EVENT_FUNCTIONAL_CALL, "FUNCTIONAL_CALL");
        registerEventCode(NdRuleAgentEventCodes.EVENT_FUNCTIONAL_RETURN, "FUNCTIONAL_RETURN");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULESET_BEGIN_PROPAGATION, "RULESET_BEGIN_PROPAGATION");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULESET_END_PROPAGATION, "RULESET_END_PROPAGATION");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULESET_BEGIN_FIRE, "RULESET_BEGIN_FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RULESET_END_FIRE, "RULESET_END_FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_PROPERTY_EXTERNAL_READ, "PROPERTY_EXTERNAL_READ");
        registerEventCode(NdRuleAgentEventCodes.EVENT_PROPERTY_EXTERNAL_WRITE, "PROPERTY_EXTERNAL_WRITE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_METHOD_EXTERNAL_INVOKE, "METHOD_EXTERNAL_INVOKE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_BEGIN_STATEMENT, "BEGIN_STATEMENT");
        registerEventCode(NdRuleAgentEventCodes.EVENT_END_STATEMENT, "END_STATEMENT");
        registerEventCode(NdRuleAgentEventCodes.EVENT_SCHEDULE, "SCHEDULE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_UNSCHEDULE, "UNSCHEDULE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_FIRE, "FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_CREATE, "CREATE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_DELETE, "DELETE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_MODIFY, "MODIFY");
        registerEventCode(NdRuleAgentEventCodes.EVENT_QUERY, "QUERY");
        registerEventCode(NdRuleAgentEventCodes.EVENT_PROPAGATE, "PROPAGATE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RECLASSIFIED, "RECLASSIFIED");
        registerEventCode(NdRuleAgentEventCodes.EVENT_CALL, "CALL");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RETURN, "RETURN");
        registerEventCode(NdRuleAgentEventCodes.EVENT_STATEMENT, "STATEMENT");
        registerEventCode(NdRuleAgentEventCodes.EVENT_RECLASSIFYING, "RECLASSIFYING");
        registerEventCode(NdRuleAgentEventCodes.EVENT_BEGIN_PROPAGATION, "BEGIN_PROPAGATION");
        registerEventCode(NdRuleAgentEventCodes.EVENT_END_PROPAGATION, "END_PROPAGATION");
        registerEventCode(NdRuleAgentEventCodes.EVENT_BEGIN_FIRE, "BEGIN_FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_END_FIRE, "END_FIRE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_EXTERNAL_READ, "EXTERNAL_READ");
        registerEventCode(NdRuleAgentEventCodes.EVENT_EXTERNAL_WRITE, "EXTERNAL_WRITE");
        registerEventCode(NdRuleAgentEventCodes.EVENT_EXTERNAL_INVOKE, "EXTERNAL_INVOKE");


        registerTraceCode(NdTraceEvent.UNSPECIFIED, "UNSPECIFIED");
        registerTraceCode(NdTraceEvent.RULE_SCHEDULED, "RULE_SCHEDULED");
        registerTraceCode(NdTraceEvent.RULE_UNSCHEDULED, "RULE_UNSCHEDULED");
        registerTraceCode(NdTraceEvent.FUNCTIONAL_CALL, "FUNCTIONAL_CALL");
        registerTraceCode(NdTraceEvent.FUNCTIONAL_RETURN, "FUNCTIONAL_RETURN");
        registerTraceCode(NdTraceEvent.RULESET_ENTERING_PROPAGATION, "RULESET_ENTERING_PROPAGATION");
        registerTraceCode(NdTraceEvent.RULESET_EXITING_PROPAGATION, "RULESET_EXITING_PROPAGATION");
        registerTraceCode(NdTraceEvent.RULESET_ENTERING_FIRE, "RULESET_ENTERING_FIRE");
        registerTraceCode(NdTraceEvent.RULESET_EXITING_FIRE, "RULESET_EXITING_FIRE");
        registerTraceCode(NdTraceEvent.STATEMENT_ENTERING, "STATEMENT_ENTERING");
        registerTraceCode(NdTraceEvent.STATEMENT_EXITING, "STATEMENT_EXITING");
        registerTraceCode(NdTraceEvent.RULEFLOW_STARTING, "RULEFLOW_STARTING");
        registerTraceCode(NdTraceEvent.RULEFLOW_ENDING, "RULEFLOW_ENDING");
        registerTraceCode(NdTraceEvent.RULEFLOW_ENTERINGBLOCK, "RULEFLOW_ENTERINGBLOCK"); 
        registerTraceCode(NdTraceEvent.RULEFLOW_EXITINGBLOCK, "RULEFLOW_EXITINGBLOCK");
        registerTraceCode(NdTraceEvent.RULEFLOW_BEGINNINGFLOW, "RULEFLOW_BEGINNINGFLOW");
        registerTraceCode(NdTraceEvent.RULEFLOW_ENDINGFLOW, "RULEFLOW_ENDINGFLOW");
        registerTraceCode(NdTraceEvent.RULEFLOW_MODIFYINGVARIABLE, "RULEFLOW_MODIFYINGVARIABLE");
        registerTraceCode(NdTraceEvent.RULEFLOW_CONTINUINGLOOP, "RULEFLOW_CONTINUINGLOOP");
        registerTraceCode(NdTraceEvent.RULEFLOW_ENDINGLOOP, "RULEFLOW_ENDINGLOOP");
        registerTraceCode(NdTraceEvent.RULEFLOW_TAKINGDECISION, "RULEFLOW_TAKINGDECISION");
        registerTraceCode(NdTraceEvent.RULEFLOW_INVOKINGTASK, "RULEFLOW_INVOKINGTASK");
        registerTraceCode(NdTraceEvent.RULEFLOW_ENDINGEVENTWAIT, "RULEFLOW_ENDINGEVENTWAIT");
        registerTraceCode(NdTraceEvent.RULE_ENTERING_FIRE, "RULE_ENTERING_FIRE");
        registerTraceCode(NdTraceEvent.RULE_EXITING_FIRE, "RULE_EXITING_FIRE");
        registerTraceCode(NdTraceEvent.RULE_FIRED, "RULE_FIRED");
        registerTraceCode(NdTraceEvent.STATEMENT_EXECUTION, "STATEMENT_EXECUTION");
        registerTraceCode(NdTraceEvent.NAMESPACE_ENTRY, "NAMESPACE_ENTRY");
        registerTraceCode(NdTraceEvent.NAMESPACE_EXIT, "NAMESPACE_EXIT");
    }

    private static void registerOriginCode(int originCode, String eventName) {
        ORIGIN_CODE_NAMES.put(new Integer(originCode), eventName);
    }


    private static void registerTraceCode(int eventCode, String eventName) {
        TRACE_EVENT_NAMES.put(new Integer(eventCode), eventName);
    }

    private static void registerEventCode(int eventCode, String eventName) {
        EVENT_CODE_NAMES.put(new Integer(eventCode), eventName);
    }

    public static String getEventCodeName(int eventCode) {
        String code = (String) EVENT_CODE_NAMES.get(new Integer(eventCode));
        return (code == null) ? ("#" + eventCode) : code;
    }

    public static String getTraceCodeName(int traceCode) {
        String code = (String) TRACE_EVENT_NAMES.get(new Integer(traceCode));
        return (code == null) ? ("#" + traceCode) : code;
    }

    public static String getOriginCodeName(int originCode) {
        String code = (String) ORIGIN_CODE_NAMES.get(new Integer(originCode));
        return (code == null) ? ("#" + originCode) : code;
    }
}
