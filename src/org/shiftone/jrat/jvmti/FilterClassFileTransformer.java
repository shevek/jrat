package org.shiftone.jrat.jvmti;


import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


/**
 * @author Jeff Drost
 */
public class FilterClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(FilterClassFileTransformer.class);
    private final MethodCriteria methodCriteria;
    private final ClassFileTransformer transformer;

    public FilterClassFileTransformer(MethodCriteria methodCriteria, ClassFileTransformer transformer) {
        Assert.assertNotNull("methodCriteria", methodCriteria);
        Assert.assertNotNull("transformer", transformer);
        this.methodCriteria = methodCriteria;
        this.transformer = transformer;
    }


    public byte[] transform(
            ClassLoader loader,
            String className,
            Class /* <?> */ classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer)
            throws IllegalClassFormatException {


        String fixedClassName = className.replace('/', '.');

        int modifiers = 0;

        if (classBeingRedefined != null) {
            modifiers = classBeingRedefined.getModifiers();
        }

        if (methodCriteria.isMatch(fixedClassName, modifiers)) {

            return transformer.transform(
                    loader,
                    className,
                    classBeingRedefined,
                    protectionDomain,
                    classfileBuffer);

        } else {

            return classfileBuffer;

        }

    }

    public String toString() {
        return "FilterClassFileTransformer[" + methodCriteria + " : " + transformer + "]";
    }
}
