package org.shiftone.jrat.inject.process;


import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DirectoryFileProcessor implements FileProcessor {

    private static final Logger LOG = Logger.getLogger(DirectoryFileProcessor.class);
    private FileProcessor fileProcessor;

    public DirectoryFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }


    public void process(Transformer transformer, InjectorOptions options, File source, File target) {

        LOG.info("process " + source);
        Assert.assertTrue("source exists", source.exists());
        Assert.assertTrue("source is directory", source.isDirectory());

        if (!target.exists()) {
            target.mkdirs();
        }

        File[] files = source.listFiles();

        for (int i = 0; i < files.length; i++) {
            File childSource = files[i];
            File childTarget = new File(target.getAbsolutePath() + File.separator + files[i].getName());

            fileProcessor.process(transformer, options, childSource, childTarget);
        }
    }
}
