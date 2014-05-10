package org.shiftone.jrat.util.jmx.dynamic;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public abstract class RunnableOperation implements Operation, Runnable {

    @Override
    public Object invoke(Object params[]) {

        run();

        return null;
    }

    @Override
    public String getReturnType() {
        return null;
    }

    public int getParameterCount() {
        return 0;
    }

    @Override
    public String getParameterName(int index) {
        return null;
    }

    public String getParameterType(int index) {
        return null;
    }

    @Override
    public String getParameterDescription(int index) {
        return null;
    }
}
