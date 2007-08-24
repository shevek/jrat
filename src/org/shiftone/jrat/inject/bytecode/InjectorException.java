package org.shiftone.jrat.inject.bytecode;

import org.shiftone.jrat.core.JRatException;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class InjectorException extends JRatException {

    public InjectorException(String message) {
        super(message);
    }


    public InjectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
