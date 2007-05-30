package org.shiftone.jrat.inject;

import org.shiftone.jrat.core.JRatException;


/**
 * @author Jeff Drost
 *
 */
public class InjectionException extends JRatException {

    public InjectionException(String message) {
        super(message);
    }


    public InjectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
