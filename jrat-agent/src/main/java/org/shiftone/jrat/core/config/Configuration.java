package org.shiftone.jrat.core.config;

import java.util.ArrayList;
import java.util.List;
import org.shiftone.jrat.core.criteria.AndMethodCriteria;
import org.shiftone.jrat.core.criteria.ClassMatcherMethodCriteria;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.criteria.NotMethodCriteria;
import org.shiftone.jrat.core.criteria.OrMethodCriteria;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.GlobMatcher;

/**
 * This class represents the overall configuration of which handlers should be
 * associated to which methods/classes.  The MethodCriteria methods on this
 * class can be used to determine what classes and methods jrat cares about
 * at a high level - perhaps to govern the behavior of injection.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Configuration implements MethodCriteria {

    private static final Logger LOG = Logger.getLogger(Configuration.class);
    private final Settings settings = new Settings();
    private final AndMethodCriteria methodCriteria = new AndMethodCriteria();
    private final OrMethodCriteria profilesCriteria = new OrMethodCriteria();
    private final OrMethodCriteria excludeCriteria = new OrMethodCriteria();
    private final List<Profile> profiles = new ArrayList<Profile>();

    public Configuration() {
        methodCriteria.addCriteria(profilesCriteria);
        methodCriteria.addCriteria(new NotMethodCriteria(excludeCriteria));
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
        profilesCriteria.addCriteria(profile.getMethodCriteria());
        return profile;
    }

    @Override
    public boolean isMatch(String className, long modifier) {
        return methodCriteria.isMatch(className, modifier);
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return methodCriteria.isMatch(className, methodName, signature, modifier);
    }

    protected List<Profile> getProfiles() {
        return profiles;
    }

    public Settings getSettings() {
        return settings;
    }

}
