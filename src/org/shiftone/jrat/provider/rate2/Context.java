package org.shiftone.jrat.provider.rate2;



/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public class Context {

    private ThreadLocal threadLocal = new ThreadLocal() {

        protected Object initialValue() {
            return new Data();
        }
    };

    public int delta(int delta) {

        Data data  = get();
        int  value = data.depth;

        data.depth = value + delta;

        return value;
    }


    public int getDepth() {
        return get().depth;
    }


    public void setDepth(int depth) {
        get().depth = depth;
    }


    private Data get() {
        return (Data) threadLocal.get();
    }


    private class Data {
        private int depth;
    }
}
