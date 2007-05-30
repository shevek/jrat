package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class IfInterfaceClassVisitor implements ClassVisitor {

    private static final Logger LOG = Logger.getLogger(IfInterfaceClassVisitor.class);
    private ClassVisitor        interfaceClassVisitor;
    private ClassVisitor        concreteClassVisitor;
    private ClassVisitor        currentVisitor;

    public IfInterfaceClassVisitor(ClassVisitor interfaceClassVisitor, ClassVisitor concreteClassVisitor) {
        this.interfaceClassVisitor = interfaceClassVisitor;
        this.concreteClassVisitor  = concreteClassVisitor;
    }


    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

        if (Modifier.isInterface(access))
        {
            currentVisitor = interfaceClassVisitor;

            // LOG.debug("is interface " + name);
        }
        else
        {
            currentVisitor = concreteClassVisitor;

            // LOG.debug("is NOT interface " + name);
        }

        currentVisitor.visit(version, access, name, signature, superName, interfaces);
    }


    public void visitSource(String source, String debug) {
        currentVisitor.visitSource(source, debug);
    }


    public void visitOuterClass(String owner, String name, String desc) {
        currentVisitor.visitOuterClass(owner, name, desc);
    }


    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return currentVisitor.visitAnnotation(desc, visible);
    }


    public void visitAttribute(Attribute attr) {
        currentVisitor.visitAttribute(attr);
    }


    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        currentVisitor.visitInnerClass(name, outerName, innerName, access);
    }


    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return currentVisitor.visitField(access, name, desc, signature, value);
    }


    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return currentVisitor.visitMethod(access, name, desc, signature, exceptions);
    }


    public void visitEnd() {

        currentVisitor.visitEnd();

        currentVisitor = null;
    }
}
