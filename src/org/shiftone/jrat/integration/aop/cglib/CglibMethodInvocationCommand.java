package org.shiftone.jrat.integration.aop.cglib;


import net.sf.cglib.proxy.MethodProxy;
import org.shiftone.jrat.api.Command;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CglibMethodInvocationCommand implements Command {

    private final Object target;
    private final Object[] args;
    private final MethodProxy methodProxy;

    public CglibMethodInvocationCommand(MethodProxy methodProxy, Object target, Object[] args) {

        this.target = target;
        this.methodProxy = methodProxy;
        this.args = args;
    }


    public Object execute() throws Throwable {
        return methodProxy.invoke(target, args);
    }
}
