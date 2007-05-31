package org.shiftone.jrat.core.config;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jeff Drost
 */
public class ConfigMethodHandlerFactory implements MethodHandlerFactory {

    private static final Logger LOG = Logger.getLogger(ConfigMethodHandlerFactory.class);
    private final List profileFactories = new ArrayList();

    public ConfigMethodHandlerFactory(Configuration configuration) {

        List profiles = configuration.getProfiles();

        for (int p = 0; p < profiles.size(); p++) {

            Profile profile = (Profile) profiles.get(p);
            String profileName = profile.getName() != null
                    ? "'" + profile.getName() + "'"
                    : String.valueOf(p);

            LOG.info("Loading profile " + profileName + "...");

            List factories = profile.getFactories();

            for (int f = 0; f < factories.size(); f++) {

                Handler handler = (Handler) factories.get(f);

                LOG.info("Loading profile " + profileName + ", factory " + f + " (" + handler.getClassName() + ")...");

                profileFactories.add(new FactoryInstance(handler.buildMethodHandlerFactory(), profile));

            }
        }
    }


    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        List methodHandlers = new ArrayList();
        Iterator iterator = profileFactories.iterator();

        while (iterator.hasNext()) {

            FactoryInstance factoryInstance = (FactoryInstance) iterator.next();

            // todo - get modifiers
            factoryInstance.addHandlerIfApplicable(methodHandlers, methodKey);

        }

        if (methodHandlers.isEmpty()) {
            return SilentMethodHandler.METHOD_HANDLER;
        } else if (methodHandlers.size() == 1) {
            return (MethodHandler) methodHandlers.get(0);
        } else {
            return new CompositeMethodHandler(methodHandlers);
        }

    }

    public void startup(RuntimeContext context) throws Exception {

        LOG.info("startup");

        Iterator iterator = profileFactories.iterator();

        while (iterator.hasNext()) {

            FactoryInstance factoryInstance = (FactoryInstance) iterator.next();
            factoryInstance.methodHandlerFactory.startup(context);

        }

    }

    private class FactoryInstance {

        private final MethodHandlerFactory methodHandlerFactory;
        private final MethodCriteria methodCriteria;


        public FactoryInstance(MethodHandlerFactory methodHandlerFactory, MethodCriteria methodCriteria) {
            Assert.assertNotNull(methodHandlerFactory);
            Assert.assertNotNull(methodCriteria);
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
