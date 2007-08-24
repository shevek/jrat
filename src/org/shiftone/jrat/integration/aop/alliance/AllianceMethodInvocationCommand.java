package org.shiftone.jrat.integration.aop.alliance;


import org.aopalliance.intercept.MethodInvocation;
import org.shiftone.jrat.api.Command;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AllianceMethodInvocationCommand implements Command {

    private final MethodInvocation methodInvocation;

    public AllianceMethodInvocationCommand(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }


    public Object execute() throws Throwable {
        return methodInvocation.proceed();
    }
}
