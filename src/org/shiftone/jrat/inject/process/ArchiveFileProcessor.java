package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.InputOutputException;
import org.shiftone.jrat.util.io.OpenInputStream;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.CompositeMatcher;
import org.shiftone.jrat.util.regex.Matcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.12 $
 */
public class ArchiveFileProcessor extends AbstractFileProcessor {

    private static final Logger  LOG               = Logger.getLogger(ArchiveFileProcessor.class);
    private static final Matcher EXTENTION_MATCHER =
        CompositeMatcher.buildCompositeGlobMatcher("zip,jar,ear,war,sar,har");
    private static final int     BUFFER_SIZE       = 1024 * 64;

    protected void processFile(Transformer transformer, InjectorOptions options, File source, File target) {

        LOG.debug("processFile " + source.getAbsolutePath() + " => " + target.getAbsolutePath());

        ZipInputStream  sourceStream = new ZipInputStream(IOUtil.openInputStream(source, BUFFER_SIZE));
        ZipOutputStream targetStream = new ZipOutputStream(IOUtil.openOutputStream(target, BUFFER_SIZE));

        targetStream.setLevel(Deflater.BEST_SPEED);

        try
        {
            processStreams(transformer, options, sourceStream, targetStream);
        }
        catch (Exception e)
        {
            throw new InjectionException("error injecting " + source.getAbsoluteFile() + " => "
                                         + target.getAbsolutePath(), e);
        }
        finally
        {
            IOUtil.close(sourceStream);
            IOUtil.close(targetStream);
        }
    }


    protected boolean processStreams(
            Transformer transformer, InjectorOptions options, ZipInputStream sourceStream, ZipOutputStream targetStream)
                throws Exception {

        Assert.assertNotNull("transformer", transformer);

        ZipEntry    inEntry  = null;
        ZipEntry    outEntry = null;
        InputStream entryInputStream;

        addReadmeCommentFile(targetStream);

        while ((inEntry = sourceStream.getNextEntry()) != null)
        {
            outEntry         = new ZipEntry(inEntry.getName());
            entryInputStream = new OpenInputStream(sourceStream);

            targetStream.putNextEntry(outEntry);

            String ext = getNormalizedExtention(inEntry);

            if (isArchiveExtention(ext))
            {
                LOG.info("Entering nested archive : " + outEntry.getName());

                ZipInputStream  nestedSourceStream = new ZipInputStream(entryInputStream);
                ZipOutputStream nestedTargetStream = new ZipOutputStream(targetStream);

                processStreams(transformer, options, nestedSourceStream, nestedTargetStream);
                nestedTargetStream.finish();    // this line seems important...

                // :)
            }
            else if (isClassExtention(ext))
            {
                LOG.debug("injecting " + inEntry.getName());

                byte[] transformedClass = transformer.inject(entryInputStream, inEntry.getName(), options);

                targetStream.write(transformedClass);
            }
            else
            {
                LOG.debug("copying " + inEntry.getName());
                IOUtil.copy(entryInputStream, targetStream);
            }

            targetStream.closeEntry();
        }

        return true;
    }


    private void addReadmeCommentFile(ZipOutputStream zipOutputStream) {

        ZipEntry entry = new ZipEntry("_READ_ME.JRAT");

        try
        {
            zipOutputStream.putNextEntry(entry);

            PrintStream printStream = new PrintStream(zipOutputStream);

            printStream.println("# This Archive file was injected by");
            printStream.println("# Shiftone JRat the Java Runtime Analysis Toolkit");
            printStream.println("# For more information, visit http://jrat.sourceforge.net");
            printStream.println("#\tVersion  : " + VersionUtil.getVersion());
            printStream.println("#\tBuilt On : " + VersionUtil.getBuiltOn());
            printStream.println("#\tBuilt By : " + VersionUtil.getBuiltBy());
            printStream.println();
            printStream.println();
            printStream.println();
            printStream.println("# the following system properties were present during injection");
            printStream.flush();
            System.getProperties().store(zipOutputStream, null);
            zipOutputStream.closeEntry();
        }
        catch (IOException e)
        {
            throw new InputOutputException("unable to add comment file to archive", e);
        }
    }


    private String getNormalizedExtention(ZipEntry entry) {
        return IOUtil.getExtention(entry.getName());
    }


    public static boolean isArchiveExtention(String ext) {
        return EXTENTION_MATCHER.isMatch(ext);
    }


    public boolean isClassExtention(String ext) {
        return (ext != null) && (ext.equals("class"));
    }
}
