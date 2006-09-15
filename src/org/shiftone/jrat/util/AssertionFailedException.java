package org.shiftone.jrat.util;



import org.shiftone.jrat.core.JRatException;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.11 $
 */
public class AssertionFailedException extends JRatException {

    public AssertionFailedException(String message) {
        super(message);
    }
}
