package org.shiftone.jrat.jvmti;


import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.core.Mode;
import org.shiftone.jrat.core.config.Configuration;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;


/**
 * -javaagent:
 */
public class Agent {

    private static final Logger LOG = Logger.getLogger(Agent.class);
    private static boolean installed = false;
    private static Configuration configuration;
    private static ClassFileTransformer transformer;


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

            //transformer = new DumpClassFileTransformer(transformer);
            
            instrumentation.addTransformer(transformer);
            installed = true;

            //LOG.info("Installed " + transformer + ".");
            //redefineAllLoadedClasses(instrumentation, transformer);

        }
        catch (Throwable e) {

            LOG.info("Installed = " + installed, e);

        }
    }


    private static void redefineAllLoadedClasses(Instrumentation instrumentation, ClassFileTransformer transformer) throws Exception {

        Class[] classes = instrumentation.getAllLoadedClasses();

        if (!instrumentation.isRedefineClassesSupported()) {
            LOG.info("RedefineClassesSupported = false");
            return;
        }


        List classDefinitions = new ArrayList();

        for (int i = 0; i < classes.length; i++) {
            Class klass = classes[i];

            if (klass.isArray()) {
                continue;
            }

            String resourceName = "/" + klass.getName().replaceAll("\\.", "/") + ".class";
            InputStream inputStream = klass.getResourceAsStream(resourceName);


            if (inputStream != null) {
                byte[] bytes = IOUtil.readAndClose(inputStream);

                byte[] newBytes = transformer.transform(
                        klass.getClassLoader(),
                        klass.getName(),
                        klass,
                        klass.getProtectionDomain(),
                        bytes);

                try {

                    //instrumentation.redefineClasses(new ClassDefinition[]{new ClassDefinition(klass, newBytes)});

                    classDefinitions.add(new ClassDefinition(klass, newBytes));

                    LOG.info("queue classes " + resourceName);

                } catch (Exception e) {

                    LOG.info("failed to reload " + klass.getName(), e);

                }
            }

            // redefine

        }

    }



}
