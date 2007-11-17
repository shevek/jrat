package org.shiftone.jrat.jvmti;


import org.shiftone.jrat.core.ServiceFactory;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


public class InjectClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(InjectClassFileTransformer.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private Transformer transformer = serviceFactory.getTransformer();
    private InjectorOptions injectorOptions;

    public InjectClassFileTransformer(InjectorOptions injectorOptions) throws Exception {

        LOG.info("new");

        this.injectorOptions = injectorOptions;
    }


    public byte[] transform(
            ClassLoader loader,
            String className,
            Class /* <?> */ classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] inClassfileBuffer)
            throws IllegalClassFormatException {

        if (className.startsWith("org/shiftone/jrat")
                || className.startsWith("sun")
                || loader == null
                ) {

            // LOG.debug("skipping class : " + className);
            return inClassfileBuffer;
        }

        return transformer.inject(inClassfileBuffer, injectorOptions);

    }


    public String toString() {
        return "InjectClassFileTransformer[" + transformer + "]";
    }
}
