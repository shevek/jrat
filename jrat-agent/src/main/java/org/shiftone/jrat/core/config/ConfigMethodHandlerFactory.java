package org.shiftone.jrat.core.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ConfigMethodHandlerFactory implements MethodHandlerFactory {

    private static final Logger LOG = Logger.getLogger(ConfigMethodHandlerFactory.class);
    private final List<FactoryInstance> profileFactories = new ArrayList<FactoryInstance>();

    public ConfigMethodHandlerFactory(Configuration configuration) {

        List<Profile> profiles = configuration.getProfiles();

        LOG.info("Loading profiles from configuration.");
        for (int p = 0; p < profiles.size(); p++) {

            Profile profile = profiles.get(p);

            String profileName = profile.getName() != null
                    ? "'" + profile.getName() + "'"
                    : String.valueOf(p);

            LOG.info("Loading profile " + profileName + "...");

            List<Handler> factories = profile.getFactories();

            for (int f = 0; f < factories.size(); f++) {
                Handler handler = factories.get(f);
                String factoryName = profileName + ", factory " + f + " (" + handler.getClassName() + ")";
                LOG.info("Loading factory " + handler + "...");

                try {

                    profileFactories.add(new FactoryInstance(handler.buildMethodHandlerFactory(), profile.getMethodCriteria()));

                } catch (Exception e) {

                    LOG.error("There was an error loading factory " + factoryName, e);
                    LOG.info("Execution will proceed, however this factory will not receieve events.");

                }

            }
        }
        LOG.info("Loaded profiles from configuration.");
    }

    @Override
    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        // LOG.info("MethodKey: " + methodKey.getFullyQualifiedClassName() + " . " + methodKey.getMethodName());
        List<MethodHandler> methodHandlers = new ArrayList<MethodHandler>();
        for (FactoryInstance factoryInstance : profileFactories) {
            // todo - get modifiers
            // LOG.info("FactoryInstance: " + factoryInstance);
            factoryInstance.addHandlerIfApplicable(methodHandlers, methodKey);
        }

        // LOG.info("Handlers: " + methodHandlers);
        if (methodHandlers.isEmpty()) {
            return SilentMethodHandler.METHOD_HANDLER;
        } else if (methodHandlers.size() == 1) {
            return methodHandlers.get(0);
        } else {
            return new CompositeMethodHandler(methodHandlers);
        }

    }

    @Override
    public void startup(RuntimeContext context) throws Exception {
        LOG.info("startup");

        for (FactoryInstance factoryInstance : profileFactories) {
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

        public void addHandlerIfApplicable(Collection<MethodHandler> methodHandlers, MethodKey methodKey) throws Exception {
            // TODO: Pass package name separately.
            if (methodCriteria.isMatch(methodKey.getFullyQualifiedClassName(), methodKey.getMethodName(), methodKey.getSignature(), 0)) {
                methodHandlers.add(methodHandlerFactory.createMethodHandler(methodKey));
            }
        }

        @Override
        public String toString() {
            return methodCriteria + " -> " + methodHandlerFactory;
        }
    }

}
