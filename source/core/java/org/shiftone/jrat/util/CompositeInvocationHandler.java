package org.shiftone.jrat.util;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CompositeInvocationHandler implements InvocationHandler {

    private static final Logger LOG = Logger.getLogger(CompositeInvocationHandler.class);
    private final Class iface;
    private final List targets = new ArrayList();
    private final Object proxy;

    public CompositeInvocationHandler(Class iface) {
        this.iface = iface;
        this.proxy = Proxy.newProxyInstance(iface.getClassLoader(), new Class[]{iface}, this);
    }


    public synchronized int getTargetCount() {
        return targets.size();
    }


    public synchronized void addTarget(Object target) {

        if (iface.isAssignableFrom(target.getClass())) {
            targets.add(target);
        } else {
            throw new JRatException("unable to add target of type " + target.getClass());
        }
    }


    public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        for (int i = 0; i < targets.size(); i++) {
            LOG.debug("invoke " + method.getName() + " " + (i + 1) + " of " + targets.size());

            Object target = targets.get(i);

            try {
                method.invoke(target, args);
            }
            catch (Throwable e) {
                LOG.error("error running method " + method.getName() + " on " + target, e);
            }
        }

        return null;
    }


    public Object getProxy() {
        return proxy;
    }
}
