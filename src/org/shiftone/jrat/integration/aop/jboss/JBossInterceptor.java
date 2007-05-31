package org.shiftone.jrat.integration.aop.jboss;


import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.shiftone.jrat.api.Monitor;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class JBossInterceptor implements Interceptor {

    public String getName() {
        return "JBossToAllianceMethodInterceptor";
    }


    public Object invoke(Invocation invocation) throws Throwable {

        if (invocation instanceof MethodInvocation) {
            MethodInvocation methodInvocation = (MethodInvocation) invocation;

            return Monitor.execute(methodInvocation.getMethod(), invocation.getTargetObject(),
                    new JBossInvocationCommand(invocation));
        } else {
            return invocation.invokeNext();
        }
    }
}
