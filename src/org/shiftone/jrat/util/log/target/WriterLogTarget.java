package org.shiftone.jrat.util.log.target;



import org.shiftone.jrat.util.io.PrintStreamWriter;
import org.shiftone.jrat.util.log.Logger;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.7 $
 */
public class WriterLogTarget implements LogTarget {

    private static final Logger   LOG          = Logger.getLogger(WriterLogTarget.class);
    public static final LogTarget SYSTEM_OUT   = new WriterLogTarget(System.out);
    public static final LogTarget SYSTEM_ERROR = new WriterLogTarget(System.err);
    private static final String   DATE_FORMAT  = "MMM/dd HH:mm:ss,SSS";
    private final Writer          writer;
    private final DateFormat      dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public WriterLogTarget(PrintStream printStream) {
        this(new PrintStreamWriter(printStream));
    }


    public WriterLogTarget(Writer writer) {
        this.writer = writer;
    }


    public boolean isLevelEnabled(String topic, int level) {
        return true;
    }


    public void log(String topic, int level, Object message, Throwable throwable) {

        StringBuffer buffer = new StringBuffer(80);

        buffer.append("JRat:");
        buffer.append(Logger.LEVEL_NAMES[level]);
        buffer.append(" (");
        buffer.append(formatDate(new Date()));
        buffer.append(") (");
        buffer.append(Thread.currentThread().getName());
        buffer.append(") ");
        buffer.append(topic);
        buffer.append(" - ");
        buffer.append(String.valueOf(message));

        if (throwable != null)
        {
            buffer.append("\n");
            buffer.append(throwableToString(throwable));
        }

        buffer.append('\n');

        synchronized (this)
        {
            try
            {
                writer.write(buffer.toString());
                writer.flush();
            }
            catch (Exception e)
            {
                System.err.print("logging failed!");
                e.printStackTrace(System.err);
            }
        }
    }


    private static String throwableToString(Throwable throwable) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter  printWriter  = new PrintWriter(stringWriter);

        printWriter.print('\t');
        printWriter.println(throwable.getMessage());
        throwable.printStackTrace(printWriter);
        printWriter.flush();

        return stringWriter.toString();
    }


    private synchronized String formatDate(Date date) {
        return dateFormat.format(new Date());
    }
}
