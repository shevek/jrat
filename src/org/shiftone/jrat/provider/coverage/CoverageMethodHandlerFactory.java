package org.shiftone.jrat.provider.coverage;

import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.util.io.IOUtil;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.Writer;
import java.io.PrintWriter;

/**
 * @Author Jeff Drost
 */
public class CoverageMethodHandlerFactory extends AbstractMethodHandlerFactory  {

    private final Set handlers = new HashSet();
    private PrintWriter printWriter;

    public synchronized MethodHandler createMethodHandler(MethodKey methodKey) {
        MethodHandler handler = new CoverageMethodHandler(methodKey);
        handlers.add(handler);
        return handler;
    }


    public synchronized void startup(RuntimeContext context) throws Exception {
        super.startup(context);
        printWriter = context.createPrintWriter(getOutputFile());
    }

    public synchronized void shutdown() {


        SortedSet sorted = new TreeSet();
        sorted.addAll(handlers);
        Iterator iterator = sorted.iterator();

        while (iterator.hasNext()) {
            
            CoverageMethodHandler handler = (CoverageMethodHandler)iterator.next();            
            printWriter.write(Long.toString(handler.count));
            printWriter.write(",");
            printWriter.write(handler.methodKey.getClassName());
            printWriter.write(",");
            printWriter.write(handler.methodKey.getMethodName());
            printWriter.write(",");
            printWriter.write(handler.methodKey.getSignature());
            printWriter.write("\n");
        }

        IOUtil.close(printWriter);
        
    }


    private class CoverageMethodHandler implements MethodHandler, Comparable {

        private final MethodKey methodKey;      
        private transient long count = 0;

        public CoverageMethodHandler(MethodKey methodKey) {
            this.methodKey = methodKey;
        }

        public MethodKey getMethodKey() {
            return methodKey;
        }

        public void onMethodStart(Object obj) {
            count++;
        }

        public void onMethodFinish(Object target, long durationNanos, Throwable throwable) {
            // nothing to do  
        }


        public int compareTo(Object o) {
            CoverageMethodHandler other = (CoverageMethodHandler)o;
            int c = (count <other.count ? -1 : (count ==other.count ? 0 : 1));
            if (c == 0) {
                c = methodKey.compareTo(other.methodKey);
            }
            return c;
        }
    }
}
