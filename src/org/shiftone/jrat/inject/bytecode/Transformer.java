package org.shiftone.jrat.inject.bytecode;



import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.InputStream;


/**
 * This class is the application's interface to the bytecode injection package.
 * Application code should not use the InjectorStrategy classes directly. <p/>
 * Node: setting the system property <b>jrat.transformer.strategy</b> will
 * change the InjectorStrategy. Avalible options are <b>bcel</b> and
 * <b>javassist</b>
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
public class Transformer implements ShutdownListener, TransformerMBean {

    private static final Logger LOG            = Logger.getLogger(Transformer.class);
    private static final String UNKNOWN_SOURCE = "[unknown source]";
    private InjectorStrategy    injectorStrategy;
    private AtomicLong          transformedClassCount = new AtomicLong();
    private AtomicLong          totalInputBytes       = new AtomicLong();
    private AtomicLong          totalOutputBytes      = new AtomicLong();
    private AtomicLong          totalTransformTime    = new AtomicLong();

    public Transformer(InjectorStrategy injectorStrategy) {
        this.injectorStrategy = injectorStrategy;
    }


    public Transformer() {

        String className = Settings.getInjectorStrategyClassName();

        try
        {
            Class  klass  = Class.forName(className);
            Object object = klass.newInstance();

            this.injectorStrategy = (InjectorStrategy) object;
        }
        catch (Exception e)
        {
            throw new InjectorException("error initalizing strategy '" + className, e);
        }
    }


    public static Transformer create() {

        String className = Settings.getInjectorStrategyClassName();

        try
        {
            Class            klass            = Class.forName(className);
            Object           object           = klass.newInstance();
            InjectorStrategy injectorStrategy = (InjectorStrategy) object;

            return new Transformer(injectorStrategy);
        }
        catch (Exception e)
        {
            throw new InjectorException("error initalizing strategy '" + className, e);
        }
    }


    public byte[] inject(byte[] inputClassData, TransformerOptions options) {
        return inject(inputClassData, UNKNOWN_SOURCE, options);
    }


    public byte[] inject(InputStream inputClassData, TransformerOptions options) {
        return inject(inputClassData, UNKNOWN_SOURCE, options);
    }


    public byte[] inject(byte[] input, String sourceName, TransformerOptions options) {

        try
        {
            long   start  = System.currentTimeMillis();
            byte[] output = injectorStrategy.inject(input, options);

            totalTransformTime.addAndGet(System.currentTimeMillis() - start);
            totalInputBytes.addAndGet(input.length);
            totalOutputBytes.addAndGet(output.length);
            transformedClassCount.incrementAndGet();

            return output;
        }
        catch (Exception e)
        {
            throw new InjectorException("error injecting : " + sourceName, e);
        }
    }


    public long getTransformedClassCount() {
        return transformedClassCount.get();
    }


    public double getAverageTransformTimeMs() {

        return (transformedClassCount.get() == 0)
               ? 0.0
               : (double) totalTransformTime.get() / (double) transformedClassCount.get();
    }


    public double getAverageBloatPercent() {
        return (double) ((totalOutputBytes.get() * 100.0) / totalInputBytes.get()) - 100.0;
    }


    public String getInjectorStrategyText() {
        return injectorStrategy.toString();
    }


    public byte[] inject(InputStream inputClassData, String sourceName, TransformerOptions options) {

        try
        {
            byte[] inputClassDataBytes = IOUtil.readAndClose(inputClassData);

            return inject(inputClassDataBytes, sourceName, options);
        }
        catch (Exception e)
        {
            throw new InjectorException("error injecting stream : " + sourceName, e);
        }
    }


    public void shutdown() {
        LOG.info("transformed " + transformedClassCount + " classe(s)");
    }


    public String toString() {
        return "Transformer[" + injectorStrategy + "]";
    }
}
