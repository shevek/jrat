package org.shiftone.jrat.inject.process;


import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;


/**
 * @author Jeff Drost
 *
 */
public class CompositeFileProcessor implements FileProcessor {

    private static final Logger          LOG                = Logger.getLogger(CompositeFileProcessor.class);
    private final ClassFileProcessor     classProcessor     = new ClassFileProcessor();
    private final ArchiveFileProcessor   archiveProcessor   = new ArchiveFileProcessor();
    private final CopyFileProcessor      fileProcessor      = new CopyFileProcessor();
    private final DirectoryFileProcessor directoryProcessor = new DirectoryFileProcessor(this);

    public void process(Transformer transformer, InjectorOptions options, File source, File target) {

        LOG.info("process " + source.getAbsolutePath() + " => " + target.getAbsolutePath());

        String ext = IOUtil.getExtention(source);

        if (ext != null)
        {
            ext = ext.toLowerCase();
        }

        LOG.debug("ext = >" + ext + "<");

        if (source.isDirectory())
        {
            LOG.debug("directoryProcessor");
            directoryProcessor.process(transformer, options, source, target);
        }
        else if ("class".equals(ext))
        {
            LOG.debug("classProcessor");
            classProcessor.process(transformer, options, source, target);
        }
        else if (archiveProcessor.isArchiveExtention(ext))
        {
            LOG.debug("archiveProcessor");
            archiveProcessor.process(transformer, options, source, target);
        }
        else
        {
            LOG.debug("copy");
            fileProcessor.processFile(transformer, source, target);
        }
    }
}
