package org.shiftone.jrat.util.io;



import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.9 $
 */
public class InputOutputException extends JRatException {

    private static final Logger LOG = Logger.getLogger(InputOutputException.class);

    public InputOutputException(String message) {
        super(message);
    }


    public InputOutputException(String message, Throwable cause) {
        super(message, cause);
    }
}
