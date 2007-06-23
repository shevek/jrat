package org.shiftone.jrat.jvmti;


import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.core.Mode;
import org.shiftone.jrat.core.config.Configuration;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;


/**
 * -javaagent:
 */
public class Agent {

    private static final Logger LOG = Logger.getLogger(Agent.class);
    private static boolean installed = false;
    private static Configuration configuration;


    public static void premain(String agentArgs, Instrumentation instrumentation) {

        Mode.set(Mode.RUNTIME);
        
        if (installed) {

            LOG.warn("one JRat Agent was already installed.");
            LOG.warn("your probably have the -javaagent arg on the command line twice");

            return;
        }

        LOG.info("Installing JRat " + VersionUtil.getVersion() + " ClassFileTransformer...");

        if (configuration == null) {
            // this must happen after the mode is set because it will
            // GET the mode locking it.
            configuration = Environment.getConfiguration();
        }

        InjectorOptions injectorOptions = new InjectorOptions();
        injectorOptions.setCriteria(configuration);

        try {

            ClassFileTransformer transformer;


            transformer = new InjectClassFileTransformer(injectorOptions);
            transformer = new FilterClassFileTransformer(configuration, transformer);

            if (configuration.getSettings().isSystemPropertyTweakingEnabled()) {
                transformer = new SystemPropertyTweakingTransformer(transformer);
            }

            transformer = new TryCatchClassFileTransformer(transformer);

            instrumentation.addTransformer(transformer);
            LOG.info("Installed " + transformer + ".");

            installed = true;
        }
        catch (Throwable e) {

            LOG.info("NOT Installed!", e);

        }
    }
}
