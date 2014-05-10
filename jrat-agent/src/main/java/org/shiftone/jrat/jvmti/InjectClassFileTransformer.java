package org.shiftone.jrat.jvmti;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import org.shiftone.jrat.core.ServiceFactory;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.log.Logger;

public class InjectClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(InjectClassFileTransformer.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final Transformer transformer = serviceFactory.getTransformer();
    private final InjectorOptions injectorOptions;

    public InjectClassFileTransformer(InjectorOptions injectorOptions) throws Exception {

        LOG.info("new");

        this.injectorOptions = injectorOptions;
    }

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class /* <?> */ classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] inClassfileBuffer)
            throws IllegalClassFormatException {

        if (className.startsWith("org/shiftone/jrat")
                || className.startsWith("sun")
                || loader == null) {

            // LOG.debug("skipping class : " + className);
            return inClassfileBuffer;
        }

        return transformer.inject(inClassfileBuffer, injectorOptions);

    }

    @Override
    public String toString() {
        return "InjectClassFileTransformer[" + transformer + "]";
    }
}
