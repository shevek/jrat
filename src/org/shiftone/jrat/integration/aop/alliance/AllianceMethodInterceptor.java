package org.shiftone.jrat.integration.aop.alliance;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.shiftone.jrat.api.Monitor;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 * @link http://aopalliance.sourceforge.net
 */
public class AllianceMethodInterceptor implements MethodInterceptor {

    private static final MethodInterceptor INSTANCE = new AllianceMethodInterceptor();

    public static MethodInterceptor getInstance() {
        return INSTANCE;
    }


    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return Monitor.execute(methodInvocation.getMethod(), methodInvocation.getThis(),
                new AllianceMethodInvocationCommand(methodInvocation));
    }
}
