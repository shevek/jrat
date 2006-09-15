package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.InputOutputException;
import org.shiftone.jrat.util.log.Logger;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.9 $
 */
public class ClassFileProcessor extends AbstractFileProcessor {

    private static final Logger LOG = Logger.getLogger(ClassFileProcessor.class);

    protected void processStream(Transformer transformer, InjectorOptions options, InputStream inputStream,
                                 OutputStream outputStream, String fileName) {

        LOG.debug("processStream");

        byte[] newClassData = transformer.inject(inputStream, fileName, options);

        try
        {
            outputStream.write(newClassData);
        }
        catch (Exception e)
        {
            throw new InputOutputException("unable to write data to output stream", e);
        }
    }
}
