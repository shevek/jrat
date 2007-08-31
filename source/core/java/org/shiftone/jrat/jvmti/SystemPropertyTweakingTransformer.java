package org.shiftone.jrat.jvmti;

import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SystemPropertyTweakingTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(SystemPropertyTweakingTransformer.class);
    private final ClassFileTransformer transformer;
    private boolean loggedDisableMessage = false;


    public SystemPropertyTweakingTransformer(ClassFileTransformer transformer) {
        this.transformer = transformer;
    }

    public byte[] transform(
            ClassLoader loader,
            String className,
            Class/*<?>*/ classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

        if (className.startsWith("org/jboss/")) {

            setSystemProperty(
                    "jboss.shutdown.forceHalt",
                    "false",
                    "You appear to be running JBoss. Tweaking system property to allow proper shutdown.");

        }

        return transformer.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);

    }

    private void setSystemProperty(String key, String value, String reason) {

        if (value.equals(System.getProperty(key))) {
            return;
        }

        LOG.info(reason);
        LOG.info("Setting system property : " + key + " = " + value);

        if (!loggedDisableMessage) {

            LOG.info("To prevent JRat from mucking with your system properties, configure 'systemPropertyTweakingEnabled' = false");
            loggedDisableMessage = true;

        }

        System.setProperty(key, value);
    }


    public String toString() {
        return "SystemPropertyTweakingTransformer[" + transformer + "]";
    }
}
