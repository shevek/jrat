package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.InputOutputException;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.8 $
 */
public class CopyFileProcessor extends AbstractFileProcessor {

    private static final Logger LOG = Logger.getLogger(CopyFileProcessor.class);

    public void processFile(Transformer transformer, File source, File target) {

        try
        {
            File s = source.getCanonicalFile();
            File t = target.getCanonicalFile();

            if (!s.equals(t))
            {
                IOUtil.copy(source, target);
            }
        }
        catch (Exception e)
        {
            throw new InputOutputException("unable to copy to : " + target, e);
        }
    }
}
