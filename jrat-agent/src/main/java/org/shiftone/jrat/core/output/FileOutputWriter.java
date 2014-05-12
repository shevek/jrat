package org.shiftone.jrat.core.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class FileOutputWriter extends FilterWriter implements FileOutput {

    private static final Logger LOG = Logger.getLogger(FileOutputWriter.class);
    private final FileOutputRegistry registry;
    private final String name;
    private boolean closed = false;

    public FileOutputWriter(FileOutputRegistry registry, Writer target, String name) {
        super(target);
        this.registry = registry;
        this.name = name;
    }

    @Override
    public synchronized void close() throws IOException {
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
        return "Writer[" + name + "]";
    }
}
