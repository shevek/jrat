package org.shiftone.jrat.core.output;


import org.shiftone.jrat.util.io.proxy.ProxyWriter;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.Writer;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class FileOutputWriter extends ProxyWriter implements FileOutput {

    private static final Logger LOG = Logger.getLogger(FileOutputWriter.class);
    private final FileOutputRegistry registry;
    private final Writer target;
    private final String name;
    private boolean closed = false;

    public FileOutputWriter(FileOutputRegistry registry, Writer target, String name) {

        this.registry = registry;
        this.target = target;
        this.name = name;
    }


    protected Writer getTarget() throws IOException {
        return target;
    }


    public synchronized void close() throws IOException {

        if (!closed) {
            LOG.info("closing");

            closed = true;

            registry.remove(this);
            super.flush();
            super.close();
        }
    }


    public String toString() {
        return "Writer[" + name + "]";
    }
}
