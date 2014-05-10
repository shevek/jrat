package org.shiftone.jrat.core.proxy;

import java.lang.reflect.Method;
import org.shiftone.jrat.api.Command;

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

    @Override
    public Object execute() throws Throwable {
        return method.invoke(target, args);
    }
}
