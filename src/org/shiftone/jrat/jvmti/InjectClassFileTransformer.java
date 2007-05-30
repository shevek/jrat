package org.shiftone.jrat.jvmti;


import org.shiftone.jrat.core.ServiceFactory;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


public class InjectClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG            = Logger.getLogger(InjectClassFileTransformer.class);
    private ServiceFactory      serviceFactory = ServiceFactory.getInstance();
    private Transformer         transformer    = serviceFactory.getTransformer();
    private InjectorOptions     injectorOptions;

    public InjectClassFileTransformer(InjectorOptions injectorOptions) throws Exception {

        LOG.info("new");

        this.injectorOptions = injectorOptions;
    }


    public byte[] transform(
            ClassLoader loader, String className, Class /* <?> */ classBeingRedefined, ProtectionDomain protectionDomain, byte[] inClassfileBuffer)
                throws IllegalClassFormatException {

        if ((loader.getParent() == null)                        //
                || className.startsWith("org/shiftone/jrat")    //
                || className.startsWith("sun")                  //
                || className.startsWith("javax"))
        {

            // LOG.debug("skipping class : " + className);
            return inClassfileBuffer;
        }

        try
        {
            return transformer.inject(inClassfileBuffer, injectorOptions);
        }
        catch (Throwable e)
        {
            LOG.info("error transforming : " + className, e);

            return inClassfileBuffer;
        }
    }


    public String toString() {
        return getClass().getName() + "[" + injectorOptions + "]";
    }
}
