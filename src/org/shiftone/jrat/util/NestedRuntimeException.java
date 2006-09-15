package org.shiftone.jrat.util;



import java.io.PrintStream;
import java.io.PrintWriter;

import java.lang.reflect.Method;


/**
 * Ok.. before Java 1.4 - exceptions did not have a cause.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.11 $
 */
public class NestedRuntimeException extends RuntimeException {

    private Throwable rootCause = null;

    public NestedRuntimeException(String message) {
        super(message);
    }


    public NestedRuntimeException(String message, Throwable rootCause) {

        super(message);

        initializeCause(rootCause);
    }


    private void initializeCause(Throwable rootCause) {

        try
        {

            // the idea here is to try to use the java 1.4 cause, but it that's
            // not possible,
            // fall back to the custom rootCause field
            // --> public synchronized Throwable initCause(Throwable cause);
            Method initCause = Throwable.class.getMethod("initCause", new Class[]{ Throwable.class });

            initCause.invoke(this, new Object[]{ rootCause });
        }
        catch (Exception e)
        {
            this.rootCause = rootCause;
        }
    }


    public Throwable getRootCause() {
        return rootCause;
    }


    public void printStackTrace() {
        printStackTrace(System.out);
    }


    public void printStackTrace(PrintStream s) {
        printStackTrace(new PrintWriter(s));
    }


    public void printStackTrace(PrintWriter s) {

        super.printStackTrace(s);

        if (rootCause != null)
        {
            s.println("** ROOT CAUSE");
            rootCause.printStackTrace(s);
        }
    }
}
