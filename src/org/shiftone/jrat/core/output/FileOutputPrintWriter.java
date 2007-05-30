package org.shiftone.jrat.core.output;


import org.shiftone.jrat.util.log.Logger;

import java.io.PrintWriter;
import java.io.Writer;


/**
 * @author Jeff Drost
 */
public class FileOutputPrintWriter extends PrintWriter implements FileOutput {

    private static final Logger LOG = Logger.getLogger(FileOutputPrintWriter.class);
    private FileOutputRegistry registry;
    private String name;
    private boolean closed = false;

    public FileOutputPrintWriter(FileOutputRegistry registry, Writer out, String name) {

        super(out);

        this.registry = registry;
        this.name = name;
    }


    public synchronized void close() {

        if (!closed) {
            LOG.info("closing");

            closed = true;

            registry.remove(this);
            super.flush();
            super.close();
        }
    }


    public String toString() {
        return "PrintWriter[" + name + "]";
    }
}
