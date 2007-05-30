package org.shiftone.jrat.util;



import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @author Jeff Drost
 *
 */
public class Exceptions {

    public static String printStackTrace(Throwable throwable) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter  printWriter  = new PrintWriter(stringWriter);

        throwable.printStackTrace(printWriter);
        printWriter.flush();

        return stringWriter.toString();
    }


    public static RuntimeException wrapAsRTE(Throwable throwable) {

        if (throwable instanceof RuntimeException)
        {
            return (RuntimeException) throwable;
        }
        else
        {
            return new RuntimeException(throwable);
        }
    }
}
