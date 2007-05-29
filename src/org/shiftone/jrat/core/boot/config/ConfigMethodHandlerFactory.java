package org.shiftone.jrat.core.boot.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.MethodKey;

import java.util.List;

/**
 * @author Jeff Drost
 */
public class ConfigMethodHandlerFactory implements MethodHandlerFactory {
    private static final Log LOG = LogFactory.getLog(ConfigMethodHandlerFactory.class);
    private ProfileFactory[] factories;
    private final List profiles ;

    public ConfigMethodHandlerFactory(Configuration configuration) {
        this.profiles = configuration.getProfiles();
    }


    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {
        
        return null;
    }

    public void startup(RuntimeContext context) throws Exception {
        factories = new  ProfileFactory[profiles.size()];
        for (int i = 0 ; i < profiles.size() ; i ++) {
             factories[i] = new ProfileFactory((Profile)profiles.get(i));
        }
    }

    private class ProfileFactory {
        private MethodHandlerFactory methodHandlerFactory;
       private final Profile profile;

        public ProfileFactory(Profile profile) {
            this.profile = profile;

        }


    }

}
