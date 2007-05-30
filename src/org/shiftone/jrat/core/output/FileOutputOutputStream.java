package org.shiftone.jrat.core.output;



import org.shiftone.jrat.util.io.proxy.ProxyOutputStream;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.OutputStream;


/**
 * @author Jeff Drost
 *
 */
public class FileOutputOutputStream extends ProxyOutputStream implements FileOutput {

    private static final Logger      LOG = Logger.getLogger(FileOutputOutputStream.class);
    private final FileOutputRegistry registry;
    private final OutputStream       target;
    private final String             name;
    private boolean                  closed = false;

    public FileOutputOutputStream(FileOutputRegistry registry, OutputStream target, String name) {

        this.registry = registry;
        this.target   = target;
        this.name     = name;
    }


    protected OutputStream getTarget() throws IOException {
        return target;
    }


    public synchronized void close() throws IOException {

        if (!closed)
        {
            LOG.info("closing");

            closed = true;

            registry.remove(this);
            super.flush();
            super.close();
        }
    }


    public String toString() {
        return "OutputStream[" + name + "]";
    }
}
