package org.shiftone.jrat.jvmti;

import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TryCatchClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(TryCatchClassFileTransformer.class);
    private final ClassFileTransformer transformer;

    public TryCatchClassFileTransformer(ClassFileTransformer transform) {
        this.transformer = transform;
    }

    public byte[] transform(
            ClassLoader loader,
            String className,
            Class/*<?>*/ classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

        try {

        return transformer.transform(
                loader,
                className,
                classBeingRedefined,
                protectionDomain,
                classfileBuffer);

        } catch (Exception e ) {

            LOG.error("failed to transform : " + className, e);
            return classfileBuffer;

        }
    }


    public String toString() {
        return "TryCatchClassFileTransformer[" + transformer + "]";
    }
}
