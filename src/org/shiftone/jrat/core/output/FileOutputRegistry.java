package org.shiftone.jrat.core.output;


import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.util.HtmlUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Stack;


/**
 * @author Jeff Drost
 *
 */
public class FileOutputRegistry implements FileOutputRegistryMBean, ShutdownListener {

    private static final Logger LOG         = Logger.getLogger(FileOutputRegistry.class);
    private Stack               fileOutputs = new Stack();

    public int getRegisteredFileOutputCount() {
        return fileOutputs.size();
    }


    public String getRegisteredFileOutputsHtml() {
        return HtmlUtil.toHtml(fileOutputs);
    }


    public synchronized OutputStream add(OutputStream outputStream, String title) {
        return (OutputStream) add(new FileOutputOutputStream(this, outputStream, title));
    }


    public synchronized Writer add(Writer writer, String title) {
        return (Writer) add(new FileOutputWriter(this, writer, title));
    }


    public synchronized PrintWriter add(PrintWriter printWriter, String title) {
        return (PrintWriter) add(new FileOutputPrintWriter(this, printWriter, title));
    }


    public synchronized FileOutput add(FileOutput fileOutput) {

        LOG.info("add " + fileOutput);
        fileOutputs.push(fileOutput);

        return fileOutput;
    }


    synchronized void remove(FileOutput fileOutput) {
        LOG.info("remove " + fileOutput);
        fileOutputs.remove(fileOutput);
    }


    public synchronized void closeFileOutputs() {

        LOG.info("closeFileOutputs " + fileOutputs);

        while (!fileOutputs.isEmpty())
        {
            close((FileOutput) fileOutputs.pop());
        }
    }


    public synchronized void flushFileOutputs() {

        LOG.info("flushFileOutputs " + fileOutputs);

        Iterator iterator = fileOutputs.iterator();

        while (iterator.hasNext())
        {
            flush((FileOutput) iterator.next());
        }
    }


    public static void close(FileOutput fileOutput) {

        if (fileOutput != null)
        {
            try
            {
                LOG.info("closing : " + fileOutput);
                fileOutput.close();
            }
            catch (Throwable e)
            {
                LOG.error("unable to close " + fileOutput, e);
            }
        }
    }


    public static void flush(FileOutput fileOutput) {

        if (fileOutput != null)
        {
            try
            {
                LOG.info("flushing : " + fileOutput);
                fileOutput.flush();
            }
            catch (Throwable e)
            {
                LOG.error("unable to flush " + fileOutput, e);
            }
        }
    }


    public void shutdown() {
        closeFileOutputs();
    }


    public String toString() {
        return "FileOutputRegistry" + fileOutputs;
    }
}
