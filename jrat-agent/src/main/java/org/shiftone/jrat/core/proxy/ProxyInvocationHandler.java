package org.shiftone.jrat.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private static final Logger LOG = Logger.getLogger(ProxyInvocationHandler.class);
    private final Object target;

    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //   return Monitor.execute(method, target, new ProxyCommand(target, method, args));
        return null;///
    }
}
