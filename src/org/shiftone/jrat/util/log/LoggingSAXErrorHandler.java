package org.shiftone.jrat.util.log;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * @author Jeff Drost
 */
public class LoggingSAXErrorHandler implements ErrorHandler {

    private static final Logger LOG = Logger.getLogger(LoggingSAXErrorHandler.class);

    public void error(SAXParseException e) throws SAXException {
        LOG.warn("parse error " + message(e));
    }


    public void fatalError(SAXParseException e) throws SAXException {
        LOG.warn("fatal parse error " + message(e));
    }


    public void warning(SAXParseException e) throws SAXException {
        LOG.warn("parse warning " + message(e));
    }


    private String message(SAXParseException e) {
        return "on line " + e.getLineNumber() + ", column " + e.getColumnNumber() + " : " + e.getMessage();
    }
}
