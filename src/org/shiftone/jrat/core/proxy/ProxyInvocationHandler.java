package org.shiftone.jrat.core.proxy;



import org.shiftone.jrat.api.Monitor;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.proxy.ProxyCommand;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private static final Logger LOG = Logger.getLogger(ProxyInvocationHandler.class);
    private final Object        target;

    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return Monitor.execute(method, target, new ProxyCommand(target, method, args));
    }
}
