package org.shiftone.jrat.integration.aop.dynaop;


import dynaop.Invocation;
import org.shiftone.jrat.api.Monitor;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DynaopInterceptor implements dynaop.Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        return Monitor.execute(invocation.getMethod(), invocation.getProxy().getProxyContext().unwrap(),
                new DynaopInvocationCommand(invocation));
    }
}
