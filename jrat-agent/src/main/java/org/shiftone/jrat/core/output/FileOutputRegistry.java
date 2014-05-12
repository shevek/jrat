package org.shiftone.jrat.core.output;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Stack;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.util.HtmlUtil;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class FileOutputRegistry implements FileOutputRegistryMBean, ShutdownListener {

    private static final Logger LOG = Logger.getLogger(FileOutputRegistry.class);
    private final Stack<FileOutput> fileOutputs = new Stack<FileOutput>();
    private final Object lock = new Object();

    @Override
    public int getRegisteredFileOutputCount() {
        synchronized (lock) {
            return fileOutputs.size();
        }
    }

    @Override
    public String getRegisteredFileOutputsHtml() {
        synchronized (lock) {
            return HtmlUtil.toHtml(fileOutputs);
        }
    }

    public OutputStream add(OutputStream outputStream, String title) {
        return add(new FileOutputOutputStream(this, outputStream, title));
    }

    public Writer add(Writer writer, String title) {
        return add(new FileOutputWriter(this, writer, title));
    }

    public PrintWriter add(PrintWriter printWriter, String title) {
        return add(new FileOutputPrintWriter(this, printWriter, title));
    }

    private <T extends FileOutput> T add(T fileOutput) {
        LOG.info("add " + fileOutput);
        synchronized (lock) {
            fileOutputs.push(fileOutput);
        }
        return fileOutput;
    }

    /* pp */ void remove(FileOutput fileOutput) {
        LOG.info("remove " + fileOutput);
        synchronized (lock) {
            fileOutputs.remove(fileOutput);
        }
    }

    @Override
    public void closeFileOutputs() {
        LOG.info("closeFileOutputs " + fileOutputs);
        synchronized (lock) {
            while (!fileOutputs.isEmpty())
                close(fileOutputs.pop());
        }
    }

    @Override
    public void flushFileOutputs() {
        LOG.info("flushFileOutputs " + fileOutputs);
        synchronized (lock) {
            for (FileOutput fileOutput : fileOutputs)
                flush(fileOutput);
        }
    }

    public static void close(FileOutput fileOutput) {
        if (fileOutput != null) {
            try {
                LOG.info("closing : " + fileOutput);
                fileOutput.close();
            } catch (Throwable e) {
                LOG.error("unable to close " + fileOutput, e);
            }
        }
    }

    public static void flush(FileOutput fileOutput) {
        if (fileOutput != null) {
            try {
                LOG.info("flushing : " + fileOutput);
                fileOutput.flush();
            } catch (Throwable e) {
                LOG.error("unable to flush " + fileOutput, e);
            }
        }
    }

    @Override
    public void shutdown() {
        closeFileOutputs();
    }

    @Override
    public String toString() {
        return "FileOutputRegistry" + fileOutputs;
    }
}
