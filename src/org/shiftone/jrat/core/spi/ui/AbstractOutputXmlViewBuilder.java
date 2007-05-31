package org.shiftone.jrat.core.spi.ui;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.XMLUtil;
import org.shiftone.jrat.util.log.LoggingSAXErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.swing.*;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author Jeff Drost
 */
public abstract class AbstractOutputXmlViewBuilder implements OutputViewBuilder {

    protected ViewContext viewContext;

    public void buildView(ViewContext context) throws Exception {

        JComponent component = null;
        InputStream inputStream = null;
        XMLReader xmlReader = null;
        ViewerXmlHandler viewerXmlHandler = null;

        this.viewContext = context;

        try {
            inputStream = context.openInputStream();
            viewerXmlHandler = new ViewerXmlHandler(this);
            xmlReader = XMLUtil.getXMLReader();

            xmlReader.setContentHandler(viewerXmlHandler);
            xmlReader.setErrorHandler(new LoggingSAXErrorHandler());
            xmlReader.parse(new InputSource(inputStream));

            component = viewerXmlHandler.getComponent();

            Assert.assertNotNull("component", component);
        }
        catch (Exception e) {
            throw new Exception("error processing XML JRat output file", e);
        }

        context.setComponent(component);
    }


    protected abstract JComponent endDocumentCreateViewer() throws Exception;


    protected abstract void startDocument() throws Exception;


    protected abstract void endElement(String qName) throws Exception;


    protected abstract void startElement(String qName, Properties atts) throws Exception;


    protected abstract void textElement(String text) throws Exception;
}
