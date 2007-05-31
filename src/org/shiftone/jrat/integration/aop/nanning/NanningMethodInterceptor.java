package org.shiftone.jrat.integration.aop.nanning;


import org.codehaus.nanning.Invocation;
import org.shiftone.jrat.api.Monitor;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class NanningMethodInterceptor implements org.codehaus.nanning.MethodInterceptor {

    public Object invoke(Invocation invocation) throws Throwable {
        return Monitor.execute(invocation.getMethod(), invocation.getTarget(),
                new NanningInvocationCommand(invocation));
    }
}
