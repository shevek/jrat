package org.shiftone.jrat.jvmti;



import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.util.log.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;

import java.security.ProtectionDomain;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.8 $
 */
public class FilterClassFileTransformer implements ClassFileTransformer {

    private static final Logger        LOG = Logger.getLogger(FilterClassFileTransformer.class);
    private final MethodCriteria       methodCriteria;
    private final ClassFileTransformer classFileTransformer;

    public FilterClassFileTransformer(MethodCriteria methodCriteria, ClassFileTransformer classFileTransformer) {
        this.methodCriteria       = methodCriteria;
        this.classFileTransformer = classFileTransformer;
    }


    public byte[] transform(
            ClassLoader loader, String className, Class /* <?> */ classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
                throws IllegalClassFormatException {

        String fixedClassName = className.replace('/', '.');

        if (methodCriteria.isMatch(fixedClassName, 0))
        {
            return classFileTransformer.transform(loader, className, classBeingRedefined, protectionDomain,
                                                  classfileBuffer);
        }
        else
        {
            return classfileBuffer;
        }
    }
}
