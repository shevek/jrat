package org.shiftone.jrat.core;



import org.shiftone.jrat.util.NestedRuntimeException;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.19 $
 */
public class JRatException extends NestedRuntimeException {

    public JRatException(String message) {
        super(message);
    }


    public JRatException(String message, Throwable cause) {
        super(message, cause);
    }
}
