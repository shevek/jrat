package org.shiftone.jrat.core.boot.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * @author Jeff Drost
 */
public class ConfigMethodHandlerFactory implements MethodHandlerFactory {

    private static final Log LOG = LogFactory.getLog(ConfigMethodHandlerFactory.class);
    private final List profileFactories = new ArrayList();
    private final List profiles;

    public ConfigMethodHandlerFactory(Configuration configuration) {

        this.profiles = configuration.getProfiles();

        for (int p = 0; p < profiles.size(); p++) {

            Profile profile = (Profile) profiles.get(p);
            List factories = profile.getFactories();

            for (int f = 0; f < factories.size(); f++) {

                Factory factory = (Factory) factories.get(f);

                MethodHandlerFactory methodHandlerFactory = factory.buildMethodHandlerFactory();
                MethodCriteria methodCriteria = profile;

                profileFactories.add(new ProfileFactory(methodHandlerFactory, methodCriteria));

            }
        }
    }


    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {
        
        List methodHandlers = new ArrayList();
        Iterator iterator = profileFactories.iterator();

        while (iterator.hasNext()) {

           ProfileFactory profileFactory = (ProfileFactory) iterator.next();

            // todo - get modifiers
           profileFactory.addHandlerIfApplicable(methodHandlers, methodKey);

        }

        if (methodHandlers.isEmpty()) {
            return SilentMethodHandler.METHOD_HANDLER;
        } else if (methodHandlers.size() == 1) {
            return (MethodHandler)methodHandlers.get(0);
        } else {
            return new CompositeMethodHandler(methodHandlers);
        }
                
    }

    public void startup(RuntimeContext context) throws Exception {

        Iterator iterator = profileFactories.iterator();

        while (iterator.hasNext()) {

            ProfileFactory profileFactory = (ProfileFactory) iterator.next();
            profileFactory.methodHandlerFactory.startup(context);

        }

    }

    // todo - come up with a better name
    private class ProfileFactory {

        private final MethodHandlerFactory methodHandlerFactory;
        private final MethodCriteria methodCriteria;


        public ProfileFactory(MethodHandlerFactory methodHandlerFactory, MethodCriteria methodCriteria) {
            this.methodHandlerFactory = methodHandlerFactory;
            this.methodCriteria = methodCriteria;
        }

        public void addHandlerIfApplicable(Collection methodHandlers, MethodKey methodKey) throws Exception {

            if (methodCriteria.isMatch(methodKey.getClassName(), methodKey.getMethodName(), methodKey.getSignature(), 0)) {
                methodHandlers.add(methodHandlerFactory.createMethodHandler(methodKey));
            }
        }
    }

}
