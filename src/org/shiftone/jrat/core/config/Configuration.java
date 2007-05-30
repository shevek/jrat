package org.shiftone.jrat.core.config;

import org.shiftone.jrat.core.criteria.*;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.GlobMatcher;

import java.util.ArrayList;
import java.util.List;


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
    private Settings settings = new Settings();
    private AndMethodCriteria methodCriteria = new AndMethodCriteria();
    private OrMethodCriteria profilesCriteria = new OrMethodCriteria();
    private OrMethodCriteria excludeCriteria = new OrMethodCriteria();
    private List profiles = new ArrayList();


    public Configuration() {

        methodCriteria.addCriteria(profilesCriteria);
        methodCriteria.addCriteria(new NotMethodCriteria(excludeCriteria));
//        addClassExclude("bsh.*");
//        addClassExclude("com.sun.*");
//        addClassExclude("EDU.oswego.*");
//        addClassExclude("gnu.*");
//        addClassExclude("org.apache.*");
//        addClassExclude("org.dom4j.*");
//        addClassExclude("org.hsqldb.*");
//        addClassExclude("org.jboss.*");
//        addClassExclude("org.jnp.*");
//        addClassExclude("$Proxy*");
        addClassExclude("java.*");
        addClassExclude("javax.*");
    }

    protected void addClassExclude(String className) {

        LOG.info("exclude " + className);

        excludeCriteria.addCriteria(
                new ClassMatcherMethodCriteria(
                        new GlobMatcher(className)
                )
        );
    }

    protected Profile createProfile() {
        Profile profile = new Profile();
        profiles.add(profile);
        profilesCriteria.addCriteria(profile);
        return profile;
    }


    public boolean isMatch(String className, long modifier) {
        return methodCriteria.isMatch(className, modifier);
    }

    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return methodCriteria.isMatch(className, methodName, signature, modifier);
    }


    protected List getProfiles() {
        return profiles;
    }

    public Settings getSettings() {
        return settings;
    }

}
