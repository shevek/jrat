package org.shiftone.jrat.jvmti;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TryCatchClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(TryCatchClassFileTransformer.class);
    private final ClassFileTransformer transformer;

    public TryCatchClassFileTransformer(ClassFileTransformer transform) {
        this.transformer = transform;
    }

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            return transformer.transform(
                    loader,
                    className,
                    classBeingRedefined,
                    protectionDomain,
                    classfileBuffer);
        } catch (Throwable e) {
            LOG.error("failed to transform : " + className, e);
            return classfileBuffer;
        }
    }

    @Override
    public String toString() {
        return "TryCatchClassFileTransformer[" + transformer + "]";
    }
}
