package org.shiftone.jrat.core.boot.config;


import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.PropertyUtil;
import org.shiftone.jrat.util.log.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff Drost
 */
public class ConfigurationParser {

    private static final Logger LOG = Logger.getLogger(ConfigurationParser.class);

    public static Configuration parse(InputStream in) {

        ConfigurationParser parser = new ConfigurationParser();
        Configuration configuration = new Configuration();
        parser.parse(configuration, in);
        return configuration;
    }

    public void parse(Configuration configuration, InputStream in) {

        LOG.info("parsing configuration...");
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(in);
            processJrat(configuration, document.getDocumentElement());

        } catch (Exception e) {

            throw new JRatException("failed to parse configuration", e);

        }
    }


    private void processJrat(Configuration configuration, Element jratElement) {

        NodeList handlers = jratElement.getElementsByTagName("handler");
        for (int i = 0; i < handlers.getLength(); i++) {
            processHandler(configuration, (Element) handlers.item(i));
        }

        NodeList settings = jratElement.getElementsByTagName("settings");
        for (int i = 0; i < handlers.getLength(); i++) {
            processSettings(configuration, (Element) settings.item(i));
        }
    }

    private void processSettings(Configuration configuration, Element settingsElement) {

        NodeList properties = settingsElement.getElementsByTagName("property");
        Map map = new HashMap();

        for (int i = 0; i < properties.getLength(); i++) {
            Element propertyElement = (Element) properties.item(i);
            map.put(propertyElement.getAttribute("name"),
                    propertyElement.getAttribute("value"));
        }

        PropertyUtil.setProperties(configuration.getSettings(), map);
    }

    private void processHandler(Configuration configuration, Element handlerElement) {

        Profile profile = configuration.createProfile();

        NodeList includes = handlerElement.getElementsByTagName("include");
        for (int i = 0; i < includes.getLength(); i++) {
            processCriteria(profile.createInclude(), (Element) includes.item(i));
        }

        NodeList excludes = handlerElement.getElementsByTagName("exclude");
        for (int i = 0; i < excludes.getLength(); i++) {
            processCriteria(profile.createExclude(), (Element) excludes.item(i));
        }

        NodeList factories = handlerElement.getElementsByTagName("handler");
        for (int i = 0; i < factories.getLength(); i++) {
            processFactory(profile.createFactory(), (Element) factories.item(i));
        }
    }

    private void processFactory(Factory factory, Element factoryElement) {
        
        // <handler factory="org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory">

        factory.setClassName(factoryElement.getAttribute("factory"));
        NodeList properties = factoryElement.getElementsByTagName("property");

        for (int i = 0; i < properties.getLength(); i++) {
            Element property = (Element) properties.item(i);
            String name = nvl(property.getAttribute("name"));
            String value = nvl(property.getAttribute("value"));
            Assert.assertNotNull("name", name);
            factory.getProperties().put(name, value);
        }
    }

    private void processCriteria(MatcherMethodCriteria criteria, Element criteriaElement) {
        criteria.setClassName(nvl(criteriaElement.getAttribute("className")));
        criteria.setMethodName(nvl(criteriaElement.getAttribute("methodName")));
        criteria.setSignature(nvl(criteriaElement.getAttribute("signature")));
    }

    private String nvl(String s) {
        if (s.trim().length() == 0) {
            return null;
        } else {
            return s;
        }
    }


}
