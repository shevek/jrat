package org.shiftone.jrat.inject.bytecode.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class IfInterfaceClassVisitor extends ClassVisitor {

    private static final Logger LOG = Logger.getLogger(IfInterfaceClassVisitor.class);
    private final ClassVisitor interfaceClassVisitor;
    private final ClassVisitor concreteClassVisitor;
    private ClassVisitor currentVisitor;

    public IfInterfaceClassVisitor(ClassVisitor interfaceClassVisitor, ClassVisitor concreteClassVisitor) {
        super(Opcodes.ASM5);
        this.interfaceClassVisitor = interfaceClassVisitor;
        this.concreteClassVisitor = concreteClassVisitor;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

        if (Modifier.isInterface(access)) {
            currentVisitor = interfaceClassVisitor;

            // LOG.debug("is interface " + name);
        } else {
            currentVisitor = concreteClassVisitor;

            // LOG.debug("is NOT interface " + name);
        }

        currentVisitor.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(String source, String debug) {
        currentVisitor.visitSource(source, debug);
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        currentVisitor.visitOuterClass(owner, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return currentVisitor.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        currentVisitor.visitAttribute(attr);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        currentVisitor.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return currentVisitor.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return currentVisitor.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {

        currentVisitor.visitEnd();

        currentVisitor = null;
    }
}
