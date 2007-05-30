package org.shiftone.jrat.core;



import org.shiftone.jrat.util.NestedRuntimeException;


/**
 * @author Jeff Drost
 *
 */
public class JRatException extends NestedRuntimeException {

    public JRatException(String message) {
        super(message);
    }


    public JRatException(String message, Throwable cause) {
        super(message, cause);
    }
}
