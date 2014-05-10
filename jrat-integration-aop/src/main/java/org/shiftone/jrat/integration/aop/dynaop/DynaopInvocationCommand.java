package org.shiftone.jrat.integration.aop.dynaop;


import dynaop.Invocation;
import org.shiftone.jrat.api.Command;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DynaopInvocationCommand implements Command {

    private final Invocation invocation;

    public DynaopInvocationCommand(Invocation invocation) {
        this.invocation = invocation;
    }


    public Object execute() throws Throwable {
        return invocation.proceed();
    }
}
