package org.shiftone.jrat.core.output;


import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.util.io.nop.NullOutputStream;
import org.shiftone.jrat.util.io.nop.NullPrintWriter;
import org.shiftone.jrat.util.io.nop.NullWriter;
import org.shiftone.jrat.util.log.Logger;

import java.io.*;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class FileOutputFactory {

    private static final Logger LOG = Logger.getLogger(FileOutputFactory.class);
    private final FileOutputRegistry fileOutputRegistry;
    private final int bufferSize;

    public FileOutputFactory(FileOutputRegistry fileOutputRegistry, int bufferSize) {

        this.fileOutputRegistry = fileOutputRegistry;
        this.bufferSize = bufferSize;

    }


    public FileOutputFactory(FileOutputRegistry fileOutputRegistry) {
        this(fileOutputRegistry, Environment.getSettings().getOutputBufferSize());
    }


    public OutputStream createOutputStreamSafely(File file) {

        try {
            return createOutputStream(file);
        }
        catch (Throwable e) {
            LOG.error("unable to create OutputStream for '" + file + "' return /dev/null");

            return NullOutputStream.INSTANCE;
        }
    }


    public Writer createWriterSafely(File file) {

        try {
            return createWriter(file);
        }
        catch (Throwable e) {
            LOG.error("unable to create Writer for '" + file + "' return /dev/null");

            return NullWriter.INSTANCE;
        }
    }


    public PrintWriter createPrintWriterSafely(File file) {

        try {
            return createPrintWriter(file);
        }
        catch (Throwable e) {
            LOG.error("unable to create PrintWriter for '" + file + "' return /dev/null");

            return NullPrintWriter.INSTANCE;
        }
    }


    public OutputStream createOutputStream(File file) throws IOException {

        LOG.info("createOutputStream " + file.getAbsolutePath());

        OutputStream out = internalCreateOutputStream(file);

        return fileOutputRegistry.add(out, file.getName());
    }


    public Writer createWriter(File file) throws IOException {

        LOG.info("createWriter " + file.getAbsolutePath());

        Writer out = new OutputStreamWriter(internalCreateOutputStream(file));

        return fileOutputRegistry.add(out, file.getName());
    }


    public PrintWriter createPrintWriter(File file) throws IOException {

        LOG.info("createPrintWriter " + file.getAbsolutePath());

        PrintWriter out = new PrintWriter(new OutputStreamWriter(internalCreateOutputStream(file)));

        return fileOutputRegistry.add(out, file.getName());
    }


    private OutputStream internalCreateOutputStream(File file) throws IOException {

        LOG.info("createOutputStream " + file.getAbsolutePath());

        File parent = file.getParentFile();

        if (!parent.exists()) {
            LOG.info("creating parent directory : " + parent.getAbsolutePath());
            parent.mkdirs();
        }

        OutputStream out = new FileOutputStream(file);

        if (bufferSize > 0) {
            out = new BufferedOutputStream(out, bufferSize);
        }

        return out;
    }


    public String toString() {
        return "FileOutputFactory[" + fileOutputRegistry + "]";
    }
}
