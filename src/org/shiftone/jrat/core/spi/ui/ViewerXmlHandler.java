package org.shiftone.jrat.core.spi.ui;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import java.util.Properties;


/**
 * @author Jeff Drost
 */
public class ViewerXmlHandler extends DefaultHandler {

    private static final Logger LOG = Logger.getLogger(ViewerXmlHandler.class);
    private AbstractOutputXmlViewBuilder xmlViewerFactory = null;
    private JComponent component = null;
    private int depth = 0;
    private long startTimeMs = 0;

    public ViewerXmlHandler(AbstractOutputXmlViewBuilder xmlViewerFactory) {

        Assert.assertNotNull("AbstractOutputXmlViewerFactory", xmlViewerFactory);

        this.xmlViewerFactory = xmlViewerFactory;
    }


    public JComponent getComponent() {
        return component;
    }


    public void characters(char[] ch, int start, int length) throws SAXException {

        try {
            xmlViewerFactory.textElement(new String(ch, start, length));
        }
        catch (Exception e) {
            throw new SAXException("characters/textElement failed");
        }
    }


    public void startDocument() throws SAXException {
        startTimeMs = System.currentTimeMillis();
    }


    public void endDocument() throws SAXException {

        try {
            LOG.info("parse took " + (System.currentTimeMillis() - startTimeMs) + "ms");

            component = xmlViewerFactory.endDocumentCreateViewer();

            LOG.info("got " + component + " from " + xmlViewerFactory);
        }
        catch (Exception e) {
            throw new SAXException("endDocument/endDocumentCreateViewer failed");
        }
    }


    private AbstractOutputXmlViewBuilder getOutputXMLViewerFactory(Properties props) throws SAXException {

        AbstractOutputXmlViewBuilder factory = null;
        String viewerClass = props.getProperty("viewer");
        Object object = null;

        if (viewerClass == null) {
            throw new SAXException("attribute not correctly defined on root XML element");
        }

        try {
            object = ResourceUtil.newInstance(viewerClass);
            factory = (AbstractOutputXmlViewBuilder) object;
        }
        catch (Exception e) {
            throw new SAXException("error creating instance of XML viewer factory", e);
        }

        return factory;
    }


    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        Properties props = new Properties();

        depth++;

        for (int i = 0; i < attributes.getLength(); i++) {
            props.put(attributes.getQName(i), attributes.getValue(i));
        }

        try {
            if (depth == 1) {

                // billjdap
                if (xmlViewerFactory == null) {
                    xmlViewerFactory = getOutputXMLViewerFactory(props);
                }

                xmlViewerFactory.startDocument();
            }

            xmlViewerFactory.startElement(qName, props);
        }
        catch (Exception e) {
            throw new SAXException(e);
        }
    }


    public void endElement(String uri, String localName, String qName) throws SAXException {

        try {
            xmlViewerFactory.endElement(qName);

            depth--;
        }
        catch (Exception e) {
            throw new SAXException(e);
        }
    }
}
