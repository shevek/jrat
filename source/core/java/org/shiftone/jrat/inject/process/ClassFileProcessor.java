package org.shiftone.jrat.inject.process;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.log.Logger;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ClassFileProcessor extends AbstractFileProcessor {

    private static final Logger LOG = Logger.getLogger(ClassFileProcessor.class);

    protected void processStream(Transformer transformer, InjectorOptions options, InputStream inputStream,
                                 OutputStream outputStream, String fileName) {

        LOG.debug("processStream");

        byte[] newClassData = transformer.inject(inputStream, fileName, options);

        try {
            outputStream.write(newClassData);
        }
        catch (Exception e) {
            throw new JRatException("unable to write data to output stream", e);
        }
    }
}
