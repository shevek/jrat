package org.shiftone.jrat.integration.aop.nanning;


import org.codehaus.nanning.Invocation;
import org.shiftone.jrat.api.Command;


/**
 * Adaptor that wraps a org.codehaus.nanning.Invocation and implements the
 * org.aopalliance.intercept.MethodInvocation interface.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class NanningInvocationCommand implements Command {

    private final Invocation invocation;

    public NanningInvocationCommand(Invocation invocation) {
        this.invocation = invocation;
    }


    public Object execute() throws Throwable {
        return invocation.invokeNext();
    }
}
