package org.shiftone.jrat.jvmti;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class DumpClassFileTransformer implements ClassFileTransformer {

    private static final Logger LOG = Logger.getLogger(DumpClassFileTransformer.class);
    private final ClassFileTransformer transformer;

    public DumpClassFileTransformer(ClassFileTransformer transformer) {
        this.transformer = transformer;
    }

    public byte[] transform(
            ClassLoader loader,
            String className,
            Class /* <?> */ classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer)
            throws IllegalClassFormatException {

        byte[] result = transformer.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);

        try {
            System.out.println(">>> " + className);
            File target = new File("dump/" + className + ".class");
            target.getParentFile().mkdirs();
            OutputStream out = new FileOutputStream(target);
            InputStream in = new ByteArrayInputStream(result);
            IOUtil.copy(in, out);
            IOUtil.close(out);
        } catch (Exception e) {
            LOG.warn("failed to write", e);
        }

        return result;
    }
}
