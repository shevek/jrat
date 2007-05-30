package org.shiftone.jrat.inject;


import org.shiftone.jrat.core.ServiceFactory;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.inject.process.CompositeFileProcessor;
import org.shiftone.jrat.inject.process.FileProcessor;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;


/**
 * @author Jeff Drost
 *
 */
public class Injector {

    private static final Logger LOG            = Logger.getLogger(Injector.class);
    public static final String  WORK_FILE_END  = "-JRatWorkFile";
    private FileProcessor       fileProcessor  = new CompositeFileProcessor();
    private InjectorOptions     options        = new InjectorOptions();
    private ServiceFactory      serviceFactory = ServiceFactory.getInstance();
    private Transformer         transformer    = serviceFactory.getTransformer();

    public void inject(File sourceFile, File targetFile) throws InjectionException {

        String sourceExt = IOUtil.getExtention(sourceFile);
        String targetExt = IOUtil.getExtention(targetFile);
        File   targetDir = targetFile.getParentFile();

        Assert.assertSame("file extentions", sourceExt, targetExt);

        // thanks Ilja Pavkovic for finding this bug
        if (targetDir != null)
        {

            // if the parent directory doesn't exist, then it must be created
            // first.
            if (!targetDir.exists() &&!targetDir.mkdirs())
            {
                throw new InjectionException("error creating parent directory of target file : " + targetDir);
            }
        }

        fileProcessor.process(transformer, options, sourceFile, targetFile);
    }


    public void inject(String source, String target) throws InjectionException {
        inject(new File(source), new File(target));
    }


    public void inject(File file) throws InjectionException {

        if (file.getName().endsWith(Injector.WORK_FILE_END))
        {
            try
            {
                IOUtil.delete(file);
            }
            catch (JRatException e)
            {
                LOG.warn("unable to delete : " + file);
            }
        }
        else
        {
            inject(file, file);
        }
    }


    public void inject(String fileName) throws InjectionException {
        inject(new File(fileName));
    }


    // ------------------------------------------------------------------------------
    public MethodCriteria getMethodCriteria() {
        return options.getCriteria();
    }


    public void setMethodCriteria(MethodCriteria criteria) {
        options.setCriteria(criteria);
    }
}
