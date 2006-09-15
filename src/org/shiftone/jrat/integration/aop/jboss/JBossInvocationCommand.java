package org.shiftone.jrat.integration.aop.jboss;



import org.jboss.aop.joinpoint.Invocation;

import org.shiftone.jrat.api.Command;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class JBossInvocationCommand implements Command {

    private final Invocation invocation;

    public JBossInvocationCommand(Invocation invocation) {
        this.invocation = invocation;
    }


    public Object execute() throws Throwable {
        return invocation.invokeNext();
    }
}
