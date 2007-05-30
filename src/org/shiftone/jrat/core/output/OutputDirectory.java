package org.shiftone.jrat.core.output;


import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.io.Dir;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * @author Jeff Drost
 *
 */
public class OutputDirectory {

    private static final Logger     LOG           = Logger.getLogger(OutputDirectory.class);
    private final List              fileList      = new ArrayList(10);
    private final NumberFormat      fileSeqFormat = new DecimalFormat("000");
    private final AtomicLong        fileSequence  = new AtomicLong();
    private final FileOutputFactory outputFactory;
    private final Dir               outputDir;

    public OutputDirectory(FileOutputFactory outputFactory, Dir outputDir) {

        Assert.assertTrue(outputDir + ".exists()", outputDir.exists());
        Assert.assertTrue(outputDir + ".isDirectory()", outputDir.isDirectory());

        this.outputFactory = outputFactory;
        this.outputDir     = outputDir;
    }


    public static OutputDirectory create(FileOutputFactory outputFactory) {
        return new OutputDirectory(outputFactory, createOutputDir());
    }


    private static Dir createOutputDir() {

        String applicationName = Settings.getApplicationName();
        Format format          = new SimpleDateFormat("yyyy-MM-dd_a-hh-mm-ss");
        Dir    parent          = Settings.getBaseDirectory();

        if (applicationName != null)
        {
            parent = parent.createChildDir(applicationName);
        }

        parent.make();

        Dir outputDir   = null;
        int maxAttempts = 100;

        while ((outputDir == null) && (maxAttempts > 0))
        {
            try
            {
                Dir dir = parent.createChildDir(format.format(new Date()));

                dir.make();

                outputDir = dir;
            }
            catch (Exception e)
            {
                pause();
            }

            maxAttempts--;
        }

        LOG.info("output DIR = " + outputDir);

        return outputDir;
    }


    private static void pause() {

        try
        {
            Thread.sleep(50);
        }
        catch (Exception e) {}
    }


    public synchronized File createFile(String fileName) {

        Assert.assertNotNull("fileName", fileName);

        long   fileNumber     = fileSequence.incrementAndGet();
        String actualFileName = fileSeqFormat.format(fileNumber) + "_" + fileName;

        fileList.add(new FileInfo(fileName, actualFileName, System.currentTimeMillis()));

        return outputDir.createChild(actualFileName);
    }


    public void writeIndexFile() {

        File        indexFile   = outputDir.createChild("index.xml");
        PrintWriter printWriter = outputFactory.createPrintWriterSafely(indexFile);
        Iterator    files       = fileList.iterator();

        printWriter.println("<output-index>");

        while (files.hasNext())
        {
            FileInfo fileInfo = (FileInfo) files.next();

            printWriter.print(" <file requestedName=\"" + fileInfo.getRequestedName() + "\"");
            printWriter.print(" actualName=\"" + fileInfo.getActualName() + "\"");
            printWriter.println(" creationTime=\"" + fileInfo.getCreationTime() + "\"/>");
        }

        printWriter.println("<output-index>");
    }


    public PrintWriter createPrintWriter(String fileName) {
        return outputFactory.createPrintWriterSafely(createFile(fileName));
    }


    public OutputStream createOutputStream(String fileName) {
        return outputFactory.createOutputStreamSafely(createFile(fileName));
    }


    public Writer createWriter(String fileName) {
        return outputFactory.createWriterSafely(createFile(fileName));
    }
}
