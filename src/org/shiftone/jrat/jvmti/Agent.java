package org.shiftone.jrat.jvmti;



import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;


/**
 * -javaagent:
 */
public class Agent {

    private static final Logger LOG       = Logger.getLogger(Agent.class);
    private static boolean      installed = false;

    public static void premain(String agentArgs, Instrumentation instrumentation) {

        if (installed)
        {
            LOG.warn("one JRat Agent was already installed.");
            LOG.warn("your probably have the -javaagent arg on the command line twice");

            return;
        }

        LOG.info("Installing JRat " + VersionUtil.getVersion() + " ClassFileTransformer...");
        LOG.info("agentArgs = " + agentArgs);

        CriteriaBuilder criteriaBuilder = new CriteriaBuilder();
        MethodCriteria  methodCriteria  = criteriaBuilder.build(agentArgs);
        InjectorOptions injectorOptions = new InjectorOptions();

        injectorOptions.setCriteria(methodCriteria);
        LOG.info("criteria = " + methodCriteria);

        try
        {
            ClassFileTransformer transformer;

            transformer = new InjectClassFileTransformer(injectorOptions);
            transformer = new FilterClassFileTransformer(methodCriteria, transformer);

            instrumentation.addTransformer(transformer);
            LOG.info("Installed " + transformer + ".");

            installed = true;
        }
        catch (Throwable e)
        {
            LOG.info("NOT Installed!", e);
        }
    }
}
