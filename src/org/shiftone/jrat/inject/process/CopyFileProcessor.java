package org.shiftone.jrat.inject.process;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;


/**
 * @author Jeff Drost
 */
public class CopyFileProcessor extends AbstractFileProcessor {

    private static final Logger LOG = Logger.getLogger(CopyFileProcessor.class);

    public void processFile(Transformer transformer, File source, File target) {

        try {
            File s = source.getCanonicalFile();
            File t = target.getCanonicalFile();

            if (!s.equals(t)) {
                IOUtil.copy(source, target);
            }
        }
        catch (Exception e) {
            throw new JRatException("unable to copy to : " + target, e);
        }
    }
}
