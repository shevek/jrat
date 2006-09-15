package org.shiftone.jrat.core;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.15 $
 */
public class ConfigurationException extends JRatException {

    public ConfigurationException(String message) {
        super(message);
    }


    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
