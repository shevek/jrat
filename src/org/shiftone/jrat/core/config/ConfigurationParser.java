package org.shiftone.jrat.core.config;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;

/**
 * @author Jeff Drost
 */
public class ConfigurationParser {

	private static final Logger LOG = Logger.getLogger(ConfigurationParser.class);

	public static Configuration parse(InputStream in) throws Exception {

		ConfigurationParser parser = new ConfigurationParser();
		Configuration configuration = new Configuration();
		parser.parse(configuration, in);
		return configuration;
	}

	public void parse(Configuration configuration, InputStream in) throws Exception {
		
		LOG.info("parsing configuration...");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document document = builder.parse(in);
		process(configuration, document.getDocumentElement());
	}


	private void process(Configuration configuration, Element jratElement) {

		NodeList handlers = jratElement.getElementsByTagName("handler");
		for (int i = 0; i < handlers.getLength(); i ++) {
			process(configuration.createHandler(), (Element) handlers.item(i));
		}
	}

	private void process(Handler handler, Element handlerElement) {

		NodeList includes = handlerElement.getElementsByTagName("include");
		for (int i = 0; i < includes.getLength(); i ++) {
			process(handler.createInclude(), (Element) includes.item(i));
		}

		NodeList excludes = handlerElement.getElementsByTagName("exclude");
		for (int i = 0; i < excludes.getLength(); i ++) {
			process(handler.createExclude(), (Element) excludes.item(i));
		}

		NodeList factories = handlerElement.getElementsByTagName("factory");
		for (int i = 0; i < factories.getLength(); i ++) {
			process(handler.createFactory(), (Element) factories.item(i));
		}
	}

	private void process(Factory factory, Element factoryElement) {

		factory.setClassName(factoryElement.getAttribute("class"));
		NodeList properties = factoryElement.getElementsByTagName("property");

		for (int i = 0; i < properties.getLength(); i ++) {
			Element property = (Element) properties.item(i);
			String name = nvl(property.getAttribute("name"));
			String value = nvl(property.getAttribute("value"));
			Assert.assertNotNull("name", name);
			Assert.assertNotNull("value", value);
			factory.getProperties().put(name, value);
		}
	}

	private void process(MatcherMethodCriteria criteria, Element criteriaElement) {
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
