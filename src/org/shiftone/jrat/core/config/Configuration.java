package org.shiftone.jrat.core.config;

import org.shiftone.jrat.core.criteria.AndMethodCriteria;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.criteria.NotMethodCriteria;
import org.shiftone.jrat.core.criteria.OrMethodCriteria;
import org.shiftone.jrat.core.criteria.ClassMatcherMethodCriteria;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.CompositeMatcher;
import org.shiftone.jrat.util.regex.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * This class represents the overall configuration of which handlers should be
 * associated to which methods/classes.  The MethodCriteria methods on this
 * class can be used to determine what classes and methods jrat cares about
 * at a high level - perhaps to govern the behavior of injection.
 *
 * @author Jeff Drost
 */
public class Configuration implements MethodCriteria {

	private static final Logger LOG = Logger.getLogger(Configuration.class);
	private AndMethodCriteria methodCriteria = new AndMethodCriteria();
	private OrMethodCriteria customCriteria = new OrMethodCriteria();
	private OrMethodCriteria excludeCriteria = new OrMethodCriteria();
	private Properties properties = new Properties(System.getProperties());
	private List handlers = new ArrayList();


	public Configuration() {
		methodCriteria.addCriteria(customCriteria);
		methodCriteria.addCriteria(new NotMethodCriteria(excludeCriteria));
		addClassExclude("bsh.*");
		addClassExclude("com.sun.*");
		addClassExclude("EDU.oswego.*");
		addClassExclude("gnu.*");
		addClassExclude("org.apache.*");
		addClassExclude("org.dom4j.*");
		addClassExclude("org.hsqldb.*");
		addClassExclude("org.jboss.*");
		addClassExclude("org.jnp.*");
		addClassExclude("$Proxy*");
	}

	private void addClassExclude(String className) {
		 LOG.info("exclude " + className);
		 Matcher matcher = CompositeMatcher.buildCompositeGlobMatcher(className);
         excludeCriteria.addCriteria(new ClassMatcherMethodCriteria(matcher));
	}

	public Handler createHandler() {
		Handler handler = new Handler();
		handlers.add(handler);
		customCriteria.addCriteria(handler);
		return handler;
	}

	public Properties getProperties() {
		return properties;
	}

	public boolean isMatch(String className, long modifier) {
		return methodCriteria.isMatch(className, modifier);
	}

	public boolean isMatch(String className, String methodName, String signature, long modifier) {
		return methodCriteria.isMatch(className, methodName, signature, modifier);
	}


}
