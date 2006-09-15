package org.shiftone.jrat.core;



import org.shiftone.jrat.util.log.Logger;


/**
 * Class ParseException
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.16 $
 */
public class ParseException extends JRatException {

    private static final Logger LOG = Logger.getLogger(ParseException.class);

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }


    public ParseException(String message) {
        super(message);
    }
}
