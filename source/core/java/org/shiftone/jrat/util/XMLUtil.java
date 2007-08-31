package org.shiftone.jrat.util;


import org.shiftone.jrat.util.log.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * This class was written in reaction to crazy changing interfaces. Uuug.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class XMLUtil {

    private static final Logger LOG = Logger.getLogger(XMLUtil.class);
    private static boolean tryReaderFactory = true;
    private static boolean tryParserFactory = true;

    /**
     * Method getXMLReader
     *
     * @return .
     * @throws SAXException
     */
    public static XMLReader getXMLReader() throws SAXException {

        SAXException exception = null;
        XMLReader reader = null;

        try {
            if (tryReaderFactory) {
                reader = getXMLReaderFromReaderFactory();
            }
        }
        catch (SAXException e) {
            exception = e;
            tryReaderFactory = false;

            LOG.info("XMLReaderFactory isn't going to work");
        }

        try {
            if ((reader == null) && (tryParserFactory)) {
                reader = getXMLReaderFromParserFactory();
            }
        }
        catch (SAXException e) {
            exception = e;
            tryParserFactory = false;
        }

        if (reader == null) {
            if (exception == null) {
                exception = new SAXException("XMLReader is null");
            }

            throw exception;
        }

        return reader;
    }


    /**
     * Method getXMLReaderFromParserFactory
     *
     * @return .
     * @throws SAXException
     */
    public static XMLReader getXMLReaderFromParserFactory() throws SAXException {

        SAXParserFactory factory = null;
        SAXParser parser = null;

        try {
            factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
        }
        catch (Exception e) {
            throw new SAXException("error creating SAXParser", e);
        }

        return parser.getXMLReader();
    }


    /**
     * Method getXMLReaderFromXMLReaderFactory
     *
     * @return .
     * @throws SAXException
     */
    public static XMLReader getXMLReaderFromReaderFactory() throws SAXException {
        return XMLReaderFactory.createXMLReader();
    }
}
