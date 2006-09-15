package org.shiftone.jrat.provider.config.xml;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.criteria.IncludeExcludeMethodCriteria;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.provider.config.CompositeMethodHandlerFactory;
import org.shiftone.jrat.provider.config.CriteriaMethodHandlerFactory;
import org.shiftone.jrat.util.BeanWrapper;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;


public class ConfigDefaultHandler extends DefaultHandler {

    private static final Logger LOG              = Logger.getLogger(ConfigDefaultHandler.class);
    private static final String DELIM            = ", ";
    private static final String ELEMENT_ROOT     = "jrat";
    private static final String ELEMENT_HANDLER  = "handler";
    private static final String ELEMENT_INCLUDE  = "include";
    private static final String ELEMENT_EXCLUDE  = "exclude";
    private static final String ELEMENT_FACTORY  = "factory";
    private static final String ELEMENT_PROPERTY = "property";
    private static final String ELEMENT_ALL      =                                 //
        ELEMENT_ROOT + DELIM                                                       //
        + ELEMENT_HANDLER + DELIM                                                  //
        + ELEMENT_INCLUDE + DELIM                                                  //
        + ELEMENT_EXCLUDE + DELIM                                                  //
        + ELEMENT_FACTORY + DELIM                                                  //
        + ELEMENT_PROPERTY;

    //
    private Stack tagStack = new Stack();

    //private ChainMethodHandlerFactory chainFactory = null;
    //private FactoryMatcher currentMatcher = null;
    private IncludeExcludeMethodCriteria  currentCriteria;
    private CompositeMethodHandlerFactory compositeMethodHandlerFactory = new CompositeMethodHandlerFactory();
    private MethodHandlerFactory          currentFactory                = null;
    private BeanWrapper                   currentFactoryWrapper         = null;    // wraps

    public ConfigDefaultHandler() {

        //this.chainFactory = chainFactory;
    }


    private void startHandler() {

        //currentMatcher = new FactoryMatcher();
        currentCriteria = new IncludeExcludeMethodCriteria();
    }


    private void endHandler() {

        // add after done being configured
        //andMethodCriteria = andMethodCriteria.optimize();
        compositeMethodHandlerFactory.add(new CriteriaMethodHandlerFactory(currentFactory, currentCriteria));

        currentCriteria = null;
    }


    private void startFactory(String className) {

        if ((className == null) || (className.length() == 0))
        {
            throw new ConfigurationException("<factory> has null or zero lenght 'class' attribute");
        }

        currentFactory        = (MethodHandlerFactory) ResourceUtil.newInstance(className);
        currentFactoryWrapper = new BeanWrapper(currentFactory);
    }


    private void endFactory() {

        //// add after done being configured
        //currentMatcher.addFactory(currentFactory);
        //currentFactory = null;
        //currentFactoryWrapper = null;
    }


    private void addMatchCriteria(boolean include, String classNamePattern, String methodNamePattern,
                                  String signaturePattern) {

        MethodCriteria criteria = null;    //new MethodCriteria(classNamePattern, methodNamePattern, signaturePattern);

        if (include)
        {
            currentCriteria.addPositive(criteria);
        }
        else
        {
            currentCriteria.addNegative(criteria);
        }
    }


    private void addProperty(String name, String value) throws ConfigurationException {

        if ((name == null) || (name.length() == 0))
        {
            throw new ConfigurationException("<property> has null or zero lenght 'name' attribute");
        }

        currentFactoryWrapper.setProperty(name, value);
    }


    public void startElement(String uri, String localName, String qName, Attributes attribs) throws SAXException {

        tagStack.push(qName);

        try
        {
            if (ELEMENT_ROOT.equals(qName))
            {

                // jrat tag.. nothing really to do
            }
            else if (ELEMENT_HANDLER.equals(qName))
            {
                startHandler();
            }
            else if (ELEMENT_FACTORY.equals(qName))
            {
                startFactory(attribs.getValue("class"));
            }
            else if (ELEMENT_INCLUDE.equals(qName) || ELEMENT_EXCLUDE.equals(qName))
            {
                addMatchCriteria(ELEMENT_INCLUDE.equals(qName),     //
                                 attribs.getValue("className"),     //
                                 attribs.getValue("methodName"),    //
                                 attribs.getValue("signature"));
            }
            else if (ELEMENT_PROPERTY.equals(qName))
            {
                addProperty(attribs.getValue("name"), attribs.getValue("value"));
            }
            else
            {
                throw new SAXException("unrecognized tag '" + qName + "' ; valid tags are " + ELEMENT_ALL);
            }
        }
        catch (Exception e)
        {
            LOG.error("tag stack when error occured : " + tagStack);

            throw new SAXException("error processing XML configuration", e);
        }
    }


    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (ELEMENT_HANDLER.equals(qName))
        {
            endHandler();
        }
        else if (ELEMENT_FACTORY.equals(qName))
        {
            endFactory();
        }

        tagStack.pop();
    }

    //public static MethodHandlerFactory buildFactory(InputStream inputStream) throws ConfigurationException {
    //
    //      InputSource inputSource = null;
    //      MethodHandlerFactory factory = null;
    //      ChainMethodHandlerFactory chainFactory = null;
    //      XMLReader xmlReader = null;
    //      SAXConfigurator configHandler = null;
    //      try {
    //              inputSource = new InputSource(inputStream);
    //              xmlReader = XMLUtil.getXMLReader();
    //              chainFactory = new ChainMethodHandlerFactory();
    //              configHandler = new SAXConfigurator(chainFactory);
    //              xmlReader.setContentHandler(configHandler);
    //              xmlReader.setErrorHandler(new LoggingSAXErrorHandler());
    //              xmlReader.parse(inputSource);
    //              if (chainFactory.getMatcherCount() == 0) {
    //                      LOG.warn("the XML configuration did not create any handlers");
    //              } else {
    //                      factory = chainFactory;
    //              }
    //      } catch (Exception e) {
    //              LOG.error("error building XML configured factory", e);
    //      }
    //      if (factory == null) {
    //              LOG.info("returning NULL handler");
    //      }
    //      return factory;
    //}
}
