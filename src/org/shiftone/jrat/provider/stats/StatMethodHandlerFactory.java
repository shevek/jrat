package org.shiftone.jrat.provider.stats;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.stats.jmx.StatMBeanRegistry;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Jeff Drost
 *
 */
public class StatMethodHandlerFactory extends AbstractMethodHandlerFactory implements StatMethodHandlerFactoryMBean {

    private static final Logger LOG         = Logger.getLogger(StatMethodHandlerFactory.class);
    private List                handlerList = new ArrayList();
    private StatMBeanRegistry   statMBeanRegistry;
    private boolean             recordUnused = true;

    public void startup(RuntimeContext context) throws Exception {

        super.startup(context);
        context.registerMBean(this);

        statMBeanRegistry = new StatMBeanRegistry(context);
    }


    public synchronized MethodHandler createMethodHandler(MethodKey methodKey) {

        StatMethodHandler handler = new StatMethodHandler(methodKey);

        handlerList.add(handler);
        statMBeanRegistry.registerMethodKeyAccumulator(handler);

        return handler;
    }


    public void setRecordUnused(boolean recordUnused) {
        this.recordUnused = recordUnused;
    }


    public long getMethodHandlerCount() {
        return handlerList.size();
    }


    public void writeOutputFile() {
        writeOutputFile(null);
    }


    public void writeOutputFile(String fileName) {

        if (fileName == null)
        {
            fileName = getDefaultOutputFileName() + ".jrat";
        }

        Writer writer = null;

        try
        {
            writer = getContext().createWriter(fileName);

            writeOutput(writer);
        }
        catch (Exception e)
        {
            LOG.error("Error writting to : " + fileName, e);
        }
        finally
        {
            IOUtil.close(writer);
        }
    }


    public String dumpOutput() {

        StringWriter writer = new StringWriter();

        writeOutput(writer);

        return writer.toString();
    }


    public synchronized void writeOutput(Writer writer) {

        StatOutput statOutput = null;

        try
        {
            statOutput = new StatOutput(writer);

            StatMethodHandler[] handlers;

            synchronized (this)
            {
                handlers = new StatMethodHandler[handlerList.size()];
                handlers = (StatMethodHandler[]) handlerList.toArray(handlers);
            }

            Arrays.sort(handlers, StatComparator.INSTANCE);
            statOutput.printStats(handlers, recordUnused);
        }
        catch (Exception e)
        {
            LOG.error("Error writting output", e);
        }
        finally
        {
            IOUtil.close(writer);
        }
    }


    public synchronized void shutdown() {

        LOG.info("shutdown...");
        writeOutputFile();
        LOG.info("shutdown complete");
    }


    public String toString() {
        return "Stats Handler Factory";
    }
}
