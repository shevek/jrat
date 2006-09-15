package org.shiftone.jrat.integration.aop.cglib;



import net.sf.cglib.proxy.MethodProxy;

import org.shiftone.jrat.api.Monitor;

import java.lang.reflect.Method;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class CglibMethodInterceptor implements net.sf.cglib.proxy.MethodInterceptor {

    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        // if (Modifier.isAbstract(method.getModifiers())) {
        // return null; // TODO ??? AbstarctMethodError
        // }
        return Monitor.execute(method, target, new CglibMethodInvocationCommand(methodProxy, target, args));
    }
}
