package org.shiftone.jrat.core.output;

import java.io.PrintWriter;
import java.io.Writer;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class FileOutputPrintWriter extends PrintWriter implements FileOutput {

    private static final Logger LOG = Logger.getLogger(FileOutputPrintWriter.class);
    private final FileOutputRegistry registry;
    private final String name;
    private boolean closed = false;

    public FileOutputPrintWriter(FileOutputRegistry registry, Writer out, String name) {

        super(out);

        this.registry = registry;
        this.name = name;
    }

    @Override
    public synchronized void close() {

        if (!closed) {
            LOG.info("closing");

            closed = true;

            registry.remove(this);
            super.flush();
            super.close();
        }
    }

    @Override
    public String toString() {
        return "PrintWriter[" + name + "]";
    }
}
