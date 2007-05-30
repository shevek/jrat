package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class ClassInitClassVisitor extends ClassAdapter implements Constants, Opcodes {

    private static final Logger LOG           = Logger.getLogger(ClassInitClassVisitor.class);
    private boolean             clinitVisited = false;
    private String              className;

    public ClassInitClassVisitor(final ClassVisitor cv) {
        super(cv);
    }


    public void visit(final int version, final int access, final String name, final String signature,
                      final String superName, final String[] interfaces) {

        clinitVisited = false;
        className     = name;

        super.visit(version, access, name, signature, superName, interfaces);
    }


    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     final String[] exceptions) {

        MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);

        if (classInitName.equals(name))
        {
            clinitVisited = true;
            visitor       = new ClassInitMethodVisitor(className, visitor);
        }

        return visitor;
    }


    public void visitEnd() {

        if (!clinitVisited)
        {

            // LOG.info("adding new <clinit> method to " + className);
            MethodVisitor clinit = visitMethod(Modifier.PRIVATE_STATIC, classInitName, classInitDesc, null, null);

            clinit.visitCode();
            clinit.visitInsn(RETURN);
            clinit.visitEnd();
        }
        else
        {

            // LOG.info("class already had <clinit> method " + className);
        }

        super.visitEnd();
    }
}
