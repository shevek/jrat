package org.shiftone.jrat.util;

import org.shiftone.jrat.core.JRatException;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class AssertionFailedException extends JRatException {

    public AssertionFailedException(String message) {
        super(message);
    }
}
