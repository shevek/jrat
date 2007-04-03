package org.shiftone.jrat.core.config;

import org.shiftone.jrat.util.log.Logger;

import java.util.Properties;


/**
 * @author Jeff Drost
 */
public class Factory {
	
	private static final Logger LOG = Logger.getLogger(Factory.class);
	private String className;
	private Properties properties = new Properties();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Properties getProperties() {
		return properties;
	}

}
