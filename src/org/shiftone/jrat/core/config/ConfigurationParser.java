package org.shiftone.jrat.core.config;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
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

        Assert.assertNotNull(jratElement);

        NodeList settings = jratElement.getElementsByTagName("settings");
        for (int i = 0; i < settings.getLength(); i++) {
            processSettings(configuration, (Element) settings.item(i));
        }

        NodeList handlers = jratElement.getElementsByTagName("profile");
        for (int i = 0; i < handlers.getLength(); i++) {
            processProfile(configuration.createProfile(), (Element) handlers.item(i));
        }


    }

    private void processSettings(Configuration configuration, Element settingsElement) {

        Assert.assertNotNull(settingsElement);

        NodeList properties = settingsElement.getElementsByTagName("property");

        Map map = new HashMap();

        for (int i = 0; i < properties.getLength(); i++) {
            Element propertyElement = (Element) properties.item(i);
            map.put(propertyElement.getAttribute("name"),
                    propertyElement.getAttribute("value"));
        }

        PropertyUtil.setProperties(configuration.getSettings(), map);
    }

    private void processProfile(Profile profile, Element profileElement) {

        Assert.assertNotNull(profileElement);

        profile.setName(profileElement.getAttribute("name"));

        NodeList includes = profileElement.getElementsByTagName("include");
        for (int i = 0; i < includes.getLength(); i++) {
            processCriteria(profile.createInclude(), (Element) includes.item(i));
        }

        NodeList excludes = profileElement.getElementsByTagName("exclude");
        for (int i = 0; i < excludes.getLength(); i++) {
            processCriteria(profile.createExclude(), (Element) excludes.item(i));
        }

        NodeList handlers = profileElement.getElementsByTagName("handler");
        for (int i = 0; i < handlers.getLength(); i++) {
            processHandler(profile.createFactory(), (Element) handlers.item(i));
        }
    }

    private void processHandler(Handler handler, Element factoryElement) {

        // <handler factory="org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory">

        handler.setClassName(factoryElement.getAttribute("factory"));
        NodeList properties = factoryElement.getElementsByTagName("property");

        for (int i = 0; i < properties.getLength(); i++) {

            Element property = (Element) properties.item(i);
            String name = nvl(property.getAttribute("name"));
            String value = nvl(property.getAttribute("value"));
            Assert.assertNotNull("name", name);
            handler.getProperties().put(name, value);

        }
    }

    private void processCriteria(MatcherMethodCriteria criteria, Element criteriaElement) {

        criteria.setClassName(nvl(criteriaElement.getAttribute("className")));
        criteria.setMethodName(nvl(criteriaElement.getAttribute("methodName")));
        criteria.setSignature(nvl(criteriaElement.getAttribute("signature")));

        LOG.info("processCriteria " + criteria);
    }

    private String nvl(String s) {
        if (s.trim().length() == 0) {
            return null;
        } else {
            return s;
        }
    }


}
