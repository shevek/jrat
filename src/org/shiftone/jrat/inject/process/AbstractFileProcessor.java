package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.11 $
 * @todo this logic should all go in the Injector.
 */
public abstract class AbstractFileProcessor implements FileProcessor {

    private static final Logger LOG                  = Logger.getLogger(AbstractFileProcessor.class);
    private static final long   DEFAULT_BUFFER_SIZE  = 1024 * 6;
    private boolean             forceOverwrite       = true;    // false;
    private boolean             overwriteNewer       = false;
    private boolean             preserveLastModified = false;

    public void process(Transformer transformer, InjectorOptions options, File source, File target)
            throws InjectionException {

        LOG.debug("process " + source.getAbsolutePath() + " " + target.getAbsolutePath());
        Assert.assertNotNull("transformer", transformer);

        long lastModified;

        if (!source.exists())
        {
            throw new InjectionException("source file does not exist : " + source);
        }

        LOG.debug("source exists");

        if (source.isDirectory())
        {
            throw new InjectionException("source file is a directory : " + source);
        }

        LOG.debug("source is real file (not dir)");

        if (source.canRead() == false)
        {
            throw new InjectionException("source file can not be read (check permissions): " + source);
        }

        LOG.debug("source can be read");

        lastModified = source.lastModified();

        if (target.exists())
        {
            LOG.debug("target exists " + target.getAbsolutePath());

            if (forceOverwrite == false)
            {
                throw new InjectionException("target exists and forceOverwrite is disabled : " + source);
            }

            if (target.isDirectory())
            {
                throw new InjectionException("target is directory : " + target);
            }

            if (target.canWrite() == false)
            {
                throw new InjectionException("unable to write to target (check permissions) : " + target);
            }

            // newer is bigger
            if (target.lastModified() > source.lastModified())
            {

                // target is newer than source
                if (!overwriteNewer)
                {
                    throw new InjectionException("target is newer than source and overwriteNewer is disabled : "
                                                 + source);
                }
            }

            processUsingSwapFile(transformer, options, source, target);
        }
        else
        {
            LOG.debug("target does not exist " + target.getAbsolutePath());
            processFile(transformer, options, source, target);
        }

        if (preserveLastModified)
        {
            target.setLastModified(lastModified);
        }
    }


    protected void processUsingSwapFile(Transformer transformer, InjectorOptions options, File source, File target) {

        LOG.debug("processUsingSwapFile " + source.getAbsolutePath() + " " + target.getAbsolutePath());

        File workFile = new File(target.getAbsolutePath() + Injector.WORK_FILE_END);

        if (workFile.exists())
        {
            LOG.info("workfile found, deleting");
            IOUtil.delete(workFile);
        }

        try
        {
            processFile(transformer, options, source, workFile);

            if (!workFile.exists())
            {
                throw new InjectionException("processFile seems to have worked, but target file doesn't exist : "
                                             + source);
            }

            IOUtil.rename(workFile, target, true);
        }
        catch (Throwable e)
        {
            String msg = "Failed to instrument " + source + " : " + e;

            if ((workFile.exists()) && (!workFile.delete()))
            {
                msg += " and couldn't delete the corrupt file " + workFile.getAbsolutePath();
            }

            throw new InjectionException(msg, e);
        }
        finally
        {
            IOUtil.deleteIfExists(workFile);
        }
    }


    protected void processFile(Transformer transformer, InjectorOptions options, File source, File target) {

        int          bufferSize   = (int) Math.min(DEFAULT_BUFFER_SIZE, source.length());
        InputStream  inputStream  = null;
        OutputStream outputStream = null;

        try
        {
            inputStream  = IOUtil.openInputStream(source, bufferSize);
            outputStream = IOUtil.openOutputStream(target, bufferSize);

            LOG.debug("calling processStream");
            processStream(transformer, options, inputStream, outputStream, source.getName());
        }
        finally
        {
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
        }
    }


    protected void processStream(
            Transformer transformer, InjectorOptions options, InputStream inputStream, OutputStream outputStream, String fileName)
                throws InjectionException {
        throw new UnsupportedOperationException("processStream should be implemented by derived class");
    }
}
