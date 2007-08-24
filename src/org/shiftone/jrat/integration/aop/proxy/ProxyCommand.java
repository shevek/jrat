package org.shiftone.jrat.integration.aop.proxy;


import org.shiftone.jrat.api.Command;

import java.lang.reflect.Method;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ProxyCommand implements Command {

    private final Object target;
    private final Method method;
    private final Object[] args;

    public ProxyCommand(Object target, Method method, Object[] args) {

        this.target = target;
        this.method = method;
        this.args = args;
    }


    public Object execute() throws Throwable {
        return method.invoke(target, args);
    }
}
