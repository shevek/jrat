package org.shiftone.jrat.util;



/**
 * @author Jeff Drost
 *
 */
public class Assert {

    public static void assertNotNull(Object obj) {

        if (obj == null)
        {
            throw new AssertionFailedException("value is unexpectedly null");
        }
    }


    public static void assertNotNull(String message, Object obj) {

        if (obj == null)
        {
            throw new AssertionFailedException("value of " + message + " is unexpectedly null");
        }
    }


    public static void assertSame(String message, Object objA, Object objB) {

        if (objA == objB)
        {
            return;
        }

        if ((objA == null) || (objB == null) || (!objA.equals(objB)))
        {
            throw new AssertionFailedException("values of " + message + " are unexpectedly not the same " + objA
                                               + " <> " + objB);
        }
    }


    public static void assertTrue(String message, boolean expression) {

        if (!expression)
        {
            throw new AssertionFailedException("value of " + message + " was unexpectedly false");
        }
    }
}
